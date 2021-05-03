package salahsh.shams.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class spref {

    public static class myApp {
        public static final String DB_NAME = "my122app.db";
    }

    private SharedPreferences sh;
    private SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;

    String masterAliasKey;


    public spref() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                masterAliasKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
        try {
            sh = EncryptedSharedPreferences.create(app.main.TAG, masterAliasKey, application.getContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            editor = sh.edit();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static SharedPreferences get() {
        if (sharedPreferences != null) {
            return sharedPreferences;

        } else {
            sharedPreferences = application.getContext().getSharedPreferences(app.main.TAG, Context.MODE_PRIVATE);
            return sharedPreferences;
        }
    }

    public boolean save(String KeyName, String KeyValye) {
        return editor.putString(KeyName, KeyValye).commit();
    }

    public String getStr(String KeyName) {
        return sh.getString(KeyName, "");


    }

    public static class myTables {
        public static final String DASH_TBL = "dash_tbl";
        public static final String LOGIN_TABLE = "LOGIN_TABLE";
        public static final String postsTransaction = "postsTransaction";
        public static final String orders = "orders";
        public static final String chatTable = "chatTable";
    }


    public static class myVluse {

        // public static final int MAIN_BACKGROUND_DEFVAL                = R.mipmap.back;

        public static final String MAIN_FONT = "MAIN_FONT";


        public static final String PNONE_NO = "PNONE_NO";
        public static final String REG_KEY = "REG_KEY";

        static spref spreff=new spref();
        public static final String getURLS                  = "https://k122.ir/1/sndurl.php";
        public static final String UPLOAD_DASH_IMAGES_URL   = spreff.getStr("UPLOAD_DASH_IMAGES_URL");


    }


    public static class userTable {
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        public static final String FAMILY = "FAMILY";
        public static final String REG_CODE = "REG_KEY";
        public static final String PNONE_NO = "PNONE_NO";

    }

    public static class dashbord_tbl {

        public static final String ID = "ID";
        public static final String LOCATION_ADDRESS = "location_address";
        public static final String TOZIHE_HADESE = "tozihe_hadese";
        public static final String VAZYATE_HADESE = "vazyate_hadese";
        public static final String PNONE_NO = "PNONE_NO";
        public static final String DATE_TIME = "date_time";
        public static final String UTM_X = "utm_x";
        public static final String UTM_Y = "utm_y";
        public static final String TAEED_SHODE = "taeed_shode";
        public static final String DIDAN = "didan";
        public static final String IMAGE = "image";

        public static final String IMAGELINK = "image_link";


    }

    public static class omourdataTBL {

        public static final String id = "id";
        public static final String nameomur             = "nameomur";
        public static final String nameraees            = "nameraees";
        public static final String emailomur            = "emailomur";
        public static final String shomaretamaseomur    = "shomaretamaseomur";
        public static final String raees_pic            = "raees_pic";
        public static final String adess_omour          = "adess_omour";
        public static final String latitude             = "latitude";
        public static final String longitude            = "longitude";


    }


}