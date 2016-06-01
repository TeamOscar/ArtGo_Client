package com.seuic.sell.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.seuic.sell.R;
import com.seuic.sell.bussiness.BusinessService;
import com.seuic.sell.constant.Common;
import com.seuic.sell.entity.Sell;
import com.seuic.sell.util.CompressImg;
import com.seuic.sell.util.FastClick;
import com.seuic.sell.util.MyDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class SellItemActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int SELECT_PIC_KITKAT = 101;
    private static final int SELECT_PIC = 102;
    private EditText et_describe,et_address,et_time,et_pirce;
    private ImageView iv_add,iv_rili;
    private Button bt_issuance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);
        initView();
    }

    private void initView(){
        et_describe = (EditText)findViewById(R.id.et_describe);
        et_address = (EditText)findViewById(R.id.et_address);
        et_time = (EditText)findViewById(R.id.et_time);
        et_time.setEnabled(false);
        et_pirce = (EditText)findViewById(R.id.et_pirce);

        iv_add = (ImageView)findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);

        iv_rili = (ImageView)findViewById(R.id.iv_rili);
        iv_rili.setOnClickListener(this);

        bt_issuance = (Button)findViewById(R.id.bt_issuance);
        bt_issuance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }

        switch (v.getId()){
            case R.id.iv_add:
                if(!b){
                    getImageFromAlbum();
                }
                break;
            case R.id.bt_issuance:
                save();
                break;
            case R.id.iv_rili:
                setTime();
                break;
        }
    }


    private void setTime() {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        Date myDate = new Date();
        d.setTime(myDate);
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dlg = new DatePickerDialog(this, this, year, month, day);
        dlg.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear += 1;
        String month = "";
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = monthOfYear + "";
        }
        String day = "";
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = dayOfMonth + "";
        }

        et_time.setText("" + year +"年"+ month+"月"+day+"日");

    }

    private void save(){
        if(TextUtils.isEmpty(et_describe.getText().toString().trim())){
            Toast.makeText(this, "请输入标签描述", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_address.getText().toString().trim())){
            Toast.makeText(this, "请输入位置", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(et_time.getText().toString().trim())){
            Toast.makeText(this, "请输入时间", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(et_pirce.getText().toString().trim())){
            Toast.makeText(this, "请输入价格", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Common.isDouble(et_pirce.getText().toString().trim())&&!Common.isInteger(et_pirce.getText().toString().trim())){
            Toast.makeText(this, "输入价格不正确，请重新输入！", Toast.LENGTH_LONG).show();
            return;
        }

        if(!b){
            Toast.makeText(this, "请添加要发布的图片", Toast.LENGTH_LONG).show();
            return;
        }

        Sell mSell = new Sell();
        mSell.setId(UUID.randomUUID().toString());
        mSell.setAddress(et_address.getText().toString().trim());
        mSell.setDescription(et_describe.getText().toString().trim());
        mSell.setPath("");
        mSell.setPrice(Double.parseDouble(et_pirce.getText().toString().trim()));
        mSell.setTime(et_time.getText().toString().trim());
        mSell.setUserId(Common.mUser.getId());

        MyDialog.getInstance().setContext(this);
        MyDialog.getInstance().show();
        MyDialog.getInstance().setDisplay("发布中...");
        BusinessService.getInstance().addSell(mSell,path);

    }

    @Override
    public void addSellCallback(boolean b) {
        super.addSellCallback(b);
        MyDialog.getInstance().dismiss();
        if(b){
            finish();
            Toast.makeText(this,"发布成功！",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"发布失败！",Toast.LENGTH_LONG).show();
            return;
        }
    }

    protected void getImageFromAlbum() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
            startActivityForResult(intent, SELECT_PIC_KITKAT);
        }else{
            startActivityForResult(intent, SELECT_PIC);
        }
    }
    private boolean b = false;
    private String path = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PIC_KITKAT||requestCode == SELECT_PIC) {
            if(resultCode==RESULT_OK){
                if(data==null){
                    Toast.makeText(this, "请选择要发布的图片！", Toast.LENGTH_LONG).show();
                }else{
                    Uri uri = data.getData();
                    String path = selectImage(this,data);
                    this.path = path;
                    iv_add.setImageBitmap(CompressImg.getimage(path));
                    b = true;
                }
            }else{
                Toast.makeText(this, "请选择要发布的图片！", Toast.LENGTH_LONG).show();
            }

        }
    }

    public static String selectImage(Context context,Intent data){
        Uri selectedImage = data.getData();
        if(selectedImage!=null){
            String uriStr=selectedImage.toString();
            String path=uriStr.substring(10,uriStr.length());
            if(path.startsWith("com.sec.android.gallery3d")){
                Log.e("SellItemActivity", "It's auto backup pic path:" + selectedImage.toString());
                return null;
            }
        }
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
