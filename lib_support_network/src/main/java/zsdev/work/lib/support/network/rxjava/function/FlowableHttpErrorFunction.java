package zsdev.work.lib.support.network.rxjava.function;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import zsdev.work.lib.support.network.exception.ExceptionHandle;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-09-12 16:12
 * Description: 网络错误处理。Flowable的Function处理异常错误后返回定制消息
 * Author: 张松
 */
public class FlowableHttpErrorFunction<T> implements Function<Throwable, Flowable<T>> {

    /**
     * 非服务器产生的异常，比如本地无网络请求，Json数据解析错误、实体转换器转换异常、接口地址无效404等各类运行时抛出异常。
     * http请求相关的错误，例如：404，403，socket timeout等
     * 数据错误抛RuntimeException，也到此处处理
     *
     * @param throwable 异常类型
     * @return 异常处理类自定义数据，onError(Throwable throwable)接收此值，返回给调用者
     */
    @Override
    public Flowable<T> apply(@NonNull Throwable throwable) {
        LogUtil.i("FlowableHttpErrorFunction", "flowable is apply: " + throwable.toString());
        //自定义异常处理类创建ResponseThrowable(异常,异常码)的对象后调用Error()方法将此对象传递下游使用，也就是在onError(Throwable t)去处理
        return Flowable.error(ExceptionHandle.handleException(throwable));
    }
}
