package zsdev.work.lib.support.dialog.normal;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import zsdev.work.lib.support.dialog.R;


/**
 * Created: by 2023-08-12 00:02
 * Description: 弹窗帮助类
 * Author: 张松
 */
public class DialogHelper implements DialogInterface.OnCancelListener {

    /**
     * 持有Activity
     */
    private final Activity mActivity;
    /**
     * dialog对象
     */
    private AlertDialog mDialog;

    /**
     * 弹窗的样式
     */
    private final int mStyle;

    /**
     * 布局解析器
     */
    private final LayoutInflater mInflater;

    /**
     * 取消弹窗的回调监听
     */
    private final OnDialogCancelListener mDialogCancelListener;

    /**
     * 不传递样式，使用默认样式
     *
     * @param mActivity        Activity
     * @param onCancelListener
     */
    public DialogHelper(Activity mActivity, OnDialogCancelListener onCancelListener) {
        this(mActivity, R.style.AppAlertDialogStyle, (OnDialogCancelListener) onCancelListener);
    }

    /**
     * 传递样式
     *
     * @param mActivity   Activity
     * @param dialogStyle 样式
     */
    public DialogHelper(Activity mActivity, int dialogStyle, OnDialogCancelListener onCancelListener) {
        this.mActivity = mActivity;
        mStyle = dialogStyle;
        mInflater = LayoutInflater.from(mActivity);
        this.mDialogCancelListener = onCancelListener;
    }

    /**
     * 显示 loading 弹窗,默认不能点击弹窗区域外关闭loading
     *
     * @param loadingTip 信息提示
     */
    public void showLoadingDialog(String loadingTip) {
        showLoadingDialog(loadingTip, true);
    }

    /**
     * 显示 loading 弹窗
     *
     * @param loadingTip 信息提示
     * @param cancelable 是否可点击弹窗区域外关闭loading
     */
    public void showLoadingDialog(String loadingTip, Boolean cancelable) {
        //解析布局
        View mDialogView = mInflater.inflate(R.layout.dialog_loading_layout, null);
        //消息
        mDialogView.<TextView>findViewById(R.id.tv_dialog_loading).setText(loadingTip);
        createAndShowDialog(mDialogView, cancelable);
    }

    /**
     * 信息提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showMessageDialog(String message) {
        createHasIconDialog(-1, message, null);
    }

    /**
     * 信息提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showMessageDialog(String message, OnDialogConfirmListener confirmListener) {
        createHasIconDialog(-1, message, confirmListener);
    }

    /**
     * 成功提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showSuccessDialog(String message) {
        createHasIconDialog(R.mipmap.icon_dialog_success, message, null);
    }

    /**
     * 成功提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showSuccessDialog(String message, OnDialogConfirmListener confirmListener) {
        createHasIconDialog(R.mipmap.icon_dialog_success, message, confirmListener);
    }

    /**
     * 警告提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showWarningDialog(String message) {
        createHasIconDialog(R.mipmap.icon_dialog_warning, message, null);
    }

    /**
     * 警告提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showWarningDialog(String message, OnDialogConfirmListener confirmListener) {
        createHasIconDialog(R.mipmap.icon_dialog_warning, message, confirmListener);
    }

    /**
     * 错误提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showErrorDialog(String message) {
        createHasIconDialog(R.mipmap.icon_dialog_error, message, null);
    }

    /**
     * 错误提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showErrorDialog(String message, OnDialogConfirmListener confirmListener) {
        createHasIconDialog(R.mipmap.icon_dialog_error, message, confirmListener);
    }

    /**
     * 显示确认弹窗
     *
     * @param message         提示信息
     * @param confirmText     确认按钮文字
     * @param cancelText      取消按钮文字
     * @param confirmListener 确认按钮点击回调
     * @param cancelListener  取消按钮点击回调
     */
    public void showConfirmDialog(String message,
                                  String confirmText,
                                  String cancelText,
                                  final OnDialogConfirmListener confirmListener,
                                  final OnDialogCancelListener cancelListener) {

        //解析布局
        View mDialogView = mInflater.inflate(R.layout.dialog_confirm_layout, null);
        //消息
        mDialogView.<TextView>findViewById(R.id.tv_dialog_message).setText(message);

        // 确定按钮
        Button confirmButton = mDialogView.findViewById(R.id.btn_confirm);
        initActionButton(confirmButton, confirmText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onDialogConfirmListener(mDialog);
                }
            }
        });

        // 取消按钮
        Button cancelButton = mDialogView.findViewById(R.id.btn_cancel);
        initActionButton(cancelButton, cancelText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onDialogCancelListener(mDialog);
                }
            }
        });

        // 创建和显示弹窗
        createAndShowDialog(mDialogView, false);

    }

    /**
     * 显示确认弹窗
     *
     * @param message         提示信息
     * @param confirmText     确认按钮文字
     * @param cancelText      取消按钮文字
     * @param confirmListener 确认按钮点击回调
     */
    public void showConfirmDialog(String message,
                                  String confirmText,
                                  String cancelText,
                                  OnDialogConfirmListener confirmListener) {

        showConfirmDialog(message, confirmText, cancelText, confirmListener, null);
    }

    /**
     * 显示确认弹窗
     *
     * @param message         提示信息
     * @param confirmListener 确认按钮点击回调
     */
    public void showConfirmDialog(String message, OnDialogConfirmListener confirmListener) {
        showConfirmDialog(message, "确定", "取消", confirmListener, null);
    }


    /**
     * 显示有图标的弹窗
     */
    private void createHasIconDialog(int icon, String message, final OnDialogConfirmListener confirmListener) {
        //解析布局
        View mDialogView = mInflater.inflate(R.layout.dialog_has_tip_message, null);
        //顶部图标
        ImageView mIconView = mDialogView.findViewById(R.id.iv_dialog_icon);
        if (icon != -1) {
            mIconView.setImageResource(icon);
        } else {
            mIconView.setVisibility(View.GONE);
        }
        //消息
        mDialogView.<TextView>findViewById(R.id.tv_dialog_message).setText(message);
        //确认按钮
        initActionButton(mDialogView.<Button>findViewById(R.id.btn_confirm), "确定",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (confirmListener != null) {
                            confirmListener.onDialogConfirmListener(mDialog);
                        }
                    }
                });
        //创建并显示
        createAndShowDialog(mDialogView, false);
    }

    /**
     * 创建和显示弹窗
     */
    private void createAndShowDialog(View mContentView, Boolean cancelable) {
        // 先关闭之前的弹窗
        dismissDialog();
        //创建弹窗
        mDialog = new AlertDialog.Builder(mActivity, mStyle)
                .setView(mContentView)
                .setCancelable(cancelable)
                .setOnCancelListener(this)
                .create();
        try {
            mDialog.show();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * 初始化点击按钮
     *
     * @param button   需要设置的按钮
     * @param showText 显示的文字
     */
    private void initActionButton(Button button, String showText, final View.OnClickListener onClickListener) {
        button.setText(showText == null ? "确定" : showText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                onClickListener.onClick(v);
            }
        });
    }

    /**
     * 关闭弹窗
     */
    public void dismissDialog() {
        if (mDialog != null) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mDialogCancelListener != null) {
            mDialogCancelListener.onDialogCancelListener(mDialog);
        }
    }
}
