package zsdev.work.lib.support.dialog.custom.loadingdrawable;

import android.content.Context;
import android.util.SparseArray;

import java.lang.reflect.Constructor;

import zsdev.work.lib.support.dialog.custom.loadingdrawable.animation.FishLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.animation.GhostsEyeLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.jump.CollisionLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.jump.DanceLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.jump.GuardLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.jump.SwapLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.rotate.GearLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.rotate.LevelLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.rotate.MaterialLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.circle.rotate.WhorlLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.goods.BalloonLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.goods.WaterBottleLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.scenery.DayNightLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.scenery.ElectricFanLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.shapechange.CircleBroodLoadingRenderer;
import zsdev.work.lib.support.dialog.custom.loadingdrawable.shapechange.CoolWaitLoadingRenderer;


/**
 * Created: by 2023-09-12 22:06
 * Description:
 * Author: 张松
 */
public final class LoadingRendererFactory {

    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS = new SparseArray<>();

    static {
        //circle rotate
        LOADING_RENDERERS.put(0, MaterialLoadingRenderer.class);
        LOADING_RENDERERS.put(1, LevelLoadingRenderer.class);
        LOADING_RENDERERS.put(2, WhorlLoadingRenderer.class);
        LOADING_RENDERERS.put(3, GearLoadingRenderer.class);
        //circle jump
        LOADING_RENDERERS.put(4, SwapLoadingRenderer.class);
        LOADING_RENDERERS.put(5, GuardLoadingRenderer.class);
        LOADING_RENDERERS.put(6, DanceLoadingRenderer.class);
        LOADING_RENDERERS.put(7, CollisionLoadingRenderer.class);
        //scenery
        LOADING_RENDERERS.put(8, DayNightLoadingRenderer.class);
        LOADING_RENDERERS.put(9, ElectricFanLoadingRenderer.class);
        //animal
        LOADING_RENDERERS.put(10, FishLoadingRenderer.class);
        LOADING_RENDERERS.put(11, GhostsEyeLoadingRenderer.class);
        //goods
        LOADING_RENDERERS.put(12, BalloonLoadingRenderer.class);
        LOADING_RENDERERS.put(13, WaterBottleLoadingRenderer.class);
        //shape change
        LOADING_RENDERERS.put(14, CircleBroodLoadingRenderer.class);
        LOADING_RENDERERS.put(15, CoolWaitLoadingRenderer.class);
    }

    private LoadingRendererFactory() {
    }

    public static LoadingRenderer createLoadingRenderer(Context context, int loadingRendererId) throws Exception {
        Class<?> loadingRendererClazz = LOADING_RENDERERS.get(loadingRendererId);
        Constructor<?>[] constructors = loadingRendererClazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }

        throw new InstantiationException();
    }
}

