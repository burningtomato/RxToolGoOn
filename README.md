# RxToolGoOn
RxTool工具集的遇到的问题，自己修改的

## RxDialogDate
1. 将RxDialogDate单独拖出来，放在一个引用内
2. 【修复问题】显示日期最高就当前
3. 【修复问题】初次打开日期选择器对话框，不能直接指向当前年

## 使用方式
```
private RxDialogDate dialogDate;
    public void showDialog(View view) {
        if (dialogDate == null) {
            dialogDate = new RxDialogDate(this);
            dialogDate.setTitle("选择时间");
            dialogDate.setDateFormat(RxDialogDate.DateFormat.年月日);
        }
        dialogDate.setOnSureClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               缺点按钮 。。。。
                dialogDate.dismiss();
            }
        });
        dialogDate.setOnCancelClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                取消按钮
                dialogDate.dismiss();
            }
        });
        dialogDate.show();
    }
 ``` 
