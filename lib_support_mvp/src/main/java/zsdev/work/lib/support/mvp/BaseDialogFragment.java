package zsdev.work.lib.support.mvp;

import androidx.appcompat.app.AlertDialog;

import zsdev.work.lib.support.dialog.normal.OnDialogCancelListener;
import zsdev.work.lib.support.dialog.normal.OnDialogConfirmListener;


/**
 * Created: by 2023-09-20 12:49
 * Description: 有弹窗的BaseFragment
 * Author: 张松
 */
public class BaseDialogFragment extends BaseFragment {
    /**
     * 显示 loading 弹窗,默认不能点击空白处进行取消
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
     * @param cancelable 能不能点击空白的地方
     */
    public void showLoadingDialog(String loadingTip, Boolean cancelable) {
        mFragmentDialogHelper.showLoadingDialog(loadingTip, cancelable);
    }

    /**
     * 信息提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showMessageDialog(String message) {
        mFragmentDialogHelper.showMessageDialog(message);
    }

    /**
     * 信息提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showMessageDialog(String message, OnDialogConfirmListener confirmListener) {
        mFragmentDialogHelper.showMessageDialog(message, confirmListener);
    }

    /**
     * 成功提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showSuccessDialog(String message) {
        mFragmentDialogHelper.showSuccessDialog(message);
    }

    /**
     * 成功提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showSuccessDialog(String message, OnDialogConfirmListener confirmListener) {
        mFragmentDialogHelper.showSuccessDialog(message, confirmListener);
    }

    /**
     * 警告提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showWarningDialog(String message) {
        mFragmentDialogHelper.showWarningDialog(message);
    }

    /**
     * 警告提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showWarningDialog(String message, OnDialogConfirmListener confirmListener) {
        mFragmentDialogHelper.showWarningDialog(message, confirmListener);
    }

    /**
     * 错误提示弹窗
     *
     * @param message 提示信息的内容
     */
    public void showErrorDialog(String message) {
        mFragmentDialogHelper.showErrorDialog(message);
    }

    /**
     * 错误提示弹窗
     *
     * @param message         提示信息的内容
     * @param confirmListener 确认按钮点击的回调
     */
    public void showErrorDialog(String message, OnDialogConfirmListener confirmListener) {
        mFragmentDialogHelper.showErrorDialog(message, confirmListener);
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

        mFragmentDialogHelper.showConfirmDialog(message, confirmText, cancelText, confirmListener, cancelListener);

    }

    /**
     * 显示确认弹窗
     *
     * @param message         提示信息
     * @param confirmText     确认按钮文字
     * @param cancelText      取消按钮文字
     * @param confirmListener 确认按钮点击回调
     */
    public void showConfirmDiaLog(String message,
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
    public void showConfirmDiaLog(String message,
                                  OnDialogConfirmListener confirmListener) {
        showConfirmDialog(message, "确定", "取消", confirmListener, null);
    }


    @Override
    public void onDialogCancelListener(AlertDialog diaLog) {
        //空实现，让子类做自己想做的事情
    }

    /**
     * 关闭弹窗
     */
    public void dismissDiaLog() {
        mFragmentDialogHelper.dismissDialog();
    }
}

