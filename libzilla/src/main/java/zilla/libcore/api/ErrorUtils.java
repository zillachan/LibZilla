package zilla.libcore.api;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by John on 2016/8/23.
 */
public class ErrorUtils {

    public static APIError parseError(Response<?> response){
        Converter<ResponseBody,APIError> converter=
                 RetrofitAPI.retrofit().responseBodyConverter(APIError.class,new Annotation[0]);
        APIError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
