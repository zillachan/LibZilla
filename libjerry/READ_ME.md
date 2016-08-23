# ImageFileCropSelector
> 此类用来调系统图册或者拍照并返回数据，支持裁减

## 使用方法

* 初始化

```
        ImageFileCropSelector selector=new ImageFileCropSelector(this);       
        selector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Log.i("图片返回成功"+file);
            }

            @Override
            public void onError() {
                Log.i("图片返回失败");
            }
        });
        
        //在Activity或Fragment中加入如下代码
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            selector.onActivityResult(requestCode, resultCode, data);
        }
        
        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            selector.onSaveInstanceState(outState);
        }
        
        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            selector.onRestoreInstanceState(savedInstanceState);
        }
        
        // Android 6.0的动态权限
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            selector.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

```
* 设置参数

```
         selector.setQuality(80);//设置图片的压缩质量0到100
         selector.setOutPutImageSize(100,100);//设置图片压缩后的输出大小               
         selector.setOpenCrop(this);//开启裁减
         selector.setCropOutWH(100,100);//设置裁减的最大宽高
         selector.setAspectXY(1,1);//设置裁减框的比例
```

* 开始选取图片

```
// 拍照选取
selector.takePhoto(this);
// 从文件选取
selector.selectImage(this);
```

* 用完后记得删除文件

所有的图片临时文件都在
`/sdcard/Android/data/{packagename}/cache/image/image_selector`
当然你也可以调用`selector.clearImages(getApplicationContext());`此方法会清除image_selector下所有临时图片