package co.flexidev.theta.annotation;

public enum BlackListEmailEnum {
    gmail,
    yahoo;

    BlackListEmailEnum() {
    }


    public static boolean isValid(String value) {
        for (BlackListEmailEnum blEmail : BlackListEmailEnum.values()) {
            if (value.toLowerCase().contains("@" + blEmail.toString())) {
                return true;
            }
        }
        return false;
    }
}
