package com.lzhenxing.javascaffold.util.job;

/**
 *
 * ClassName: TargetMapField <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/9/9
 */
public class TargetMapField {

    private String originalKey;
    private String originalKeyCN;
    private String forceValidate;
    private String mapKey;
    private String KeyResource;
    private String mapKeyCN;

    public String getOriginalKey() {
        return originalKey;
    }

    public void setOriginalKey(String originalKey) {
        this.originalKey = originalKey;
    }

    public String getOriginalKeyCN() {
        return originalKeyCN;
    }

    public void setOriginalKeyCN(String originalKeyCN) {
        this.originalKeyCN = originalKeyCN;
    }

    public String getForceValidate() {
        return forceValidate;
    }

    public void setForceValidate(String forceValidate) {
        this.forceValidate = forceValidate;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getKeyResource() {
        return KeyResource;
    }

    public void setKeyResource(String keyResource) {
        KeyResource = keyResource;
    }

    public String getMapKeyCN() {
        return mapKeyCN;
    }

    public void setMapKeyCN(String mapKeyCN) {
        this.mapKeyCN = mapKeyCN;
    }
}
