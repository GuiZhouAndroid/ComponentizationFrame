package zsdev.work.lib.support.all.core.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created: by 2023-10-03 20:01
 * Description: 没有序列化和使用 Serializable 序列化的对象使用 withObject 方法传递，使用 Parcelable 方式序列化的对象则采用 withParcelable 方法进行传递
 * Author: 张松
 */
@Route(path = "/service/json")
public class AlRouterJsonServiceImpl implements SerializationService {
    private Gson mGson;

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        checkJson();
        return mGson.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        checkJson();
        return mGson.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        checkJson();
        return mGson.fromJson(input, clazz);
    }

    @Override
    public void init(Context context) {
        mGson = new Gson();
    }

    public void checkJson() {
        if (mGson == null) {
            mGson = new Gson();
        }
    }
}