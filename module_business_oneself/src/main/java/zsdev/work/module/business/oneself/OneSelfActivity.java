package zsdev.work.module.business.oneself;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import zsdev.work.lib.frame.core.arouter.AliRouterPathManager;
import zsdev.work.lib.frame.core.bean.User;
import zsdev.work.lib.support.mvp.BaseActivity;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-10-07 21:46
 * Description:
 * Author: 张松
 */
@Route(path = AliRouterPathManager.MODULE_BUSINESS_ONESELF_ACTIVITY, name = "我的")
public class OneSelfActivity extends BaseActivity {

    @Autowired(name = "myInfo1")
    public float aLong;
    @Autowired(name = "myInfo2")
    public String string;

    // 支持解析自定义对象，URL中使用json传递
    @Autowired
    User ser;


    // 使用 withObject 传递 List 和 Map 的实现了
    // Serializable 接口的实现类(ArrayList/HashMap)
    // 的时候，接收该对象的地方不能标注具体的实现类类型
    // 应仅标注为 List 或 Map，否则会影响序列化中类型
    // 的判断, 其他类似情况需要同样处理
//    @Autowired
//    List<User> list;
//    @Autowired
//    Map<String, List<User>> map;

    @Override
    public int viewByResIdBindLayout() {
        return R.layout.onself_activity;
    }

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stringExtra = getIntent().getStringExtra("组件开发【我的】");
        LogUtil.i(TAG, "onCreate: " + stringExtra);

        //注入ARouter
        ARouter.getInstance().inject(this);
//        SerializationService serializationService = ARouter.getInstance().navigation(AlRouterJsonServiceImpl.class);
//        serializationService.init(this);
//        User obj = serializationService.parseObject(getIntent().getStringExtra("obj"), User.class);
//        SerializationService serializationService = ARouter.getInstance().navigation(SerializationService.class);
//        serializationService.init(this);
//        obj = serializationService.parseObject(getIntent().getStringExtra("myInfo"), User.class);

//        long key3 = getIntent().getExtras().getLong("myInfo1");
//        String s = getIntent().getExtras().getString("myInfo2");

        if (aLong != 0 && string != null && ser != null) {
            shortShowMsg("myInfo1=" + aLong + ",myInfo2=" + string + ",ser=" + ser.toString());
        } else {
            longShowMsg("当前是开发模式！");
        }
    }
}
