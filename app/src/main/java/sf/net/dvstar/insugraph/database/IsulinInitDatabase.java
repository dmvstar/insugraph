package sf.net.dvstar.insugraph.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sf.net.dvstar.insugraph.insulin.InsulinConstants;

public class IsulinInitDatabase {

    private static final String TAG = "IsulinInitDatabase";

    public IsulinInitDatabase(){

    }

    public boolean isCreated(){
        boolean ret;
        try {
            ret = new Select().from(InsulinDuration.class).exists();
        } catch (Exception e) { ret = false; }
        return ret;
    }


    public void initCreate(){
        if(!isCreated()) {
            InsulinDuration iInsulinDuration;
            iInsulinDuration = new InsulinDuration("USHORT", "The ultra short-acting insulin preparation");    iInsulinDuration.save();
            iInsulinDuration = new InsulinDuration("SHORT", "The short-acting insulin preparation");           iInsulinDuration.save();
            iInsulinDuration = new InsulinDuration("MEDIUM", "The intermediate-acting insulin preparation");   iInsulinDuration.save();
            iInsulinDuration = new InsulinDuration("LONG", "The Long-acting insulin preparations");            iInsulinDuration.save();

            //--------------------------------------------------------------------------------------
            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'USHORT'").executeSingle();
            InsulinType iInsulinType;
            iInsulinType = new InsulinType("UHN", iInsulinDuration,	"H",	"H",	"Human");            iInsulinType.save();
            iInsulinType = new InsulinType("UHS", iInsulinDuration,	"H",	"S",	"Synthetics");            iInsulinType.save();

            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'SHORT'").executeSingle();
            iInsulinType = new InsulinType("SHN", iInsulinDuration,	"H",	"H",	"Human");            iInsulinType.save();
            iInsulinType = new InsulinType("SHS", iInsulinDuration,	"H",	"S",	"Synthetics");            iInsulinType.save();
            iInsulinType = new InsulinType("SMP", iInsulinDuration,	"A",	"P",	"Mono-peak");            iInsulinType.save();

            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'MEDIUM'").executeSingle();
            iInsulinType = new InsulinType("MHN", iInsulinDuration,	"H",	"H",	"Human");            iInsulinType.save();
            iInsulinType = new InsulinType("MHS", iInsulinDuration,	"H",	"S",	"Synthetics");            iInsulinType.save();
            iInsulinType = new InsulinType("MMC", iInsulinDuration,	"A",	"C",	"Mono-compound");            iInsulinType.save();
            iInsulinType = new InsulinType("MMP", iInsulinDuration,	"A",	"P",	"Mono-peak");            iInsulinType.save();

            iInsulinDuration = new Select().from(InsulinDuration.class).where("code = 'LONG'").executeSingle();
            iInsulinType = new InsulinType("LHN", iInsulinDuration,	"H",	"H",	"Human");            iInsulinType.save();
            iInsulinType = new InsulinType("LHS", iInsulinDuration,	"H",	"S",	"Synthetics");            iInsulinType.save();
            iInsulinType = new InsulinType("LMC", iInsulinDuration,	"A",	"C",	"Mono-compound");            iInsulinType.save();
            iInsulinType = new InsulinType("LMP", iInsulinDuration,	"A",	"P",	"Mono-peak");            iInsulinType.save();

            //--------------------------------------------------------------------------------------
            InsulinFirm iInsulinFirm;
            iInsulinFirm = new InsulinFirm("NOVO", "Novo Nordisk");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("AVENTIS", "Aventis (Hoechst)");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("BCHEM", "Berlin-Chemie");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("GALEN", "Galenika");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("INDAR", "Indar");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("LILLY", "Lilly");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("PLIVA", "Pliva");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("POLFA", "Polfa");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("NOVOP", "Novo Nordisk Polfa");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("ICNGAL", "ICN Galenika");iInsulinFirm.save();
            iInsulinFirm = new InsulinFirm("USSR", "SNG");iInsulinFirm.save();

            InsulinOrigin iInsulinOrigin;
            iInsulinOrigin = new InsulinOrigin("HBIOS", "human biosynthetic");iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("HSEMI", "human semisynthetic");iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("PORK", "pork");iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("BEEF", "beef");iInsulinOrigin.save();
            iInsulinOrigin = new InsulinOrigin("BEPO", "beef-pork");iInsulinOrigin.save();


            InsulinItem iInsulinItem;

            iInsulinFirm   = new Select().from(InsulinFirm.class).where("code = 'NOVO'").executeSingle();
            iInsulinOrigin = new Select().from(InsulinOrigin.class).where("code = 'HBIOS'").executeSingle();
            iInsulinType   = new Select().from(InsulinType.class).where("code = 'UHN'").executeSingle();
            iInsulinItem   = new InsulinItem("Novorapid", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_NOVORAPID);
            iInsulinItem.save();

            iInsulinFirm   = new Select().from(InsulinFirm.class).where("code = 'AVENTIS'").executeSingle();
            iInsulinItem = new InsulinItem("Apidra", iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_APIDRA);
            iInsulinItem.save();

            iInsulinFirm   = new Select().from(InsulinFirm.class).where("code = 'NOVO'").executeSingle();

            //iInsulinOrigin = new Select().from(InsulinOrigin.class).where("code = 'HBIOS'").executeSingle();
            iInsulinType   = new Select().from(InsulinType.class).where("code = 'SHN'").executeSingle();
            iInsulinItem = new InsulinItem("Actrapid HM",   iInsulinType, iInsulinFirm, iInsulinOrigin, 20, "m", 1, "h", 6, "h", InsulinConstants.COLOR_ACTRAPID);
            iInsulinItem.save();

            iInsulinType   = new Select().from(InsulinType.class).where("code = 'MHS'").executeSingle();
            iInsulinItem = new InsulinItem("Levemir",   iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_LEVEMIR);
            iInsulinItem.save();

            iInsulinType   = new Select().from(InsulinType.class).where("code = 'MHN'").executeSingle();
            iInsulinItem = new InsulinItem("Protaphane HM",   iInsulinType, iInsulinFirm, iInsulinOrigin, 5, "m", 1, "h", 4, "h", InsulinConstants.COLOR_PROTAFAN);
            iInsulinItem.save();


        }
    }

    public void dropTables(){
        SQLiteDatabase db = ActiveAndroid.getDatabase();
        List<String> tables = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence")) {
                tables.add(tableName);
            }
            cursor.moveToNext();
        }
        cursor.close();
        for (String tableName : tables) {
            Log.v(TAG, "Drop table " + tableName);
            //db.execSQL("DROP TABLE " + tableName);
        }

        db.close();
        SQLiteDatabase.deleteDatabase( new File(db.getPath()) );

    }


}
