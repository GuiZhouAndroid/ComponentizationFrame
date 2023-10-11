package zsdev.work.lib.support.network.rxjava.function;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.functions.Function;
import zsdev.work.lib.support.network.base.BaseResponse;
import zsdev.work.lib.support.network.exception.ServerException;


/**
 * Created: by 2023-09-12 00:12
 * Description: 处理错误码与成功数据返回。Observable + Flowable 的Function处理数据解析
 * Author: 张松
 */
public class HandlerHttpResponseFunction<T> implements Function<BaseResponse<T>, T> {
    /**
     * Code==200表示请求服务器且响应成功
     * Code!=200表示请求数据异常，如服务器返回非200的错误Code，但依然可以判定已经通过接口访问了服务器
     * 500以上错误，大部分是服务器引起的错误
     *
     * @param tBaseResponse 上游值BaseResponse
     * @return T对象数据实体
     */
    @Override
    public T apply(@NonNull BaseResponse<T> tBaseResponse) {
        // 匿名实现Function装载上游值，isServerError()判断响应结果，T为下游值
        // 当返回code在200~299区间中视为请求成功时直接返回T对象数据实体，也是就是onNext(T tData)，然后Model层通过View引用调用UI显示
        if (tBaseResponse.isSuccess()) {
            // 数据正常返回
            return tBaseResponse.getData();
        }
        // 前后端自定义约束服务器异常，将msg值装载，手动抛异常，在RxJava中发送异常一定会调用onError() 方法，下游onErrorResumeNext能够处理
        // 此处抛异常，将通过onErrorResumeNext()发射异常数据到HttpErrorFunction事件中处理异常数据
        throw new ServerException(tBaseResponse.getCode(), tBaseResponse.getMsg() != null ? tBaseResponse.getMsg() : "请求发生错误！");
    }
}
