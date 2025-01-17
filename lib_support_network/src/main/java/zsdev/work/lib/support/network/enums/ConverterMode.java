package zsdev.work.lib.support.network.enums;

/**
 * Created: by 2023-09-05 12:54
 * Description: Retrofit实体转换器模式，都需要继承Converter.Factory
 * Author: 张松
 */
public enum ConverterMode {

    /**
     * gson转换器
     */
    GSON,

    /**
     * jackson转换器
     */
    JACKSON,

    /**
     * 字符串转换器
     */
    SCALARS,

    /**
     * moshi转换器
     */
    MOSHI,

    /**
     * simplexml转换器
     */
    SIMPLE_XML,

    /**
     * wire转换器（因要添加额外支持库，此项不作支持）
     */
    WIRE,

    /**
     * protobuf转换器（因要添加额外支持库，此项不作支持）
     */
    PROTOCOL_BUFFERS,

    /**
     * jaxb转换器（因要添加额外支持库，此项不作支持）
     */
    JAXB,

    /**
     * java8转换器（因要添加额外支持库，此项不作支持）
     */
    JAVA8,

    /**
     * guava转换器（因要添加额外支持库，此项不作支持）
     */
    GUAVA,

    /**
     * 定制转换器
     */
    CUSTOM;
}
