#pragma version(1)
#pragma rs java_package_name ( com.android.rssample)

static const float4 weight = {0.299 f , 0.587 f , 0.114 f , 0.0 f };

uchar4 RS_KERNEL toGray2 ( uchar4 in ) {

    const float4 pixelf = rsUnpackColor8888 ( in ) ;
    const float gray = dot ( pixelf , weight ) ;

    return rsPackColorTo8888 ( gray , gray , gray , pixelf . a ) ;

}
