package sf.net.dvstar.insugraph.database;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sdv on 18.10.15.
 */
@Table(name = "InsulinOrigin")
public class InsulinOrigin {
    /*
    id	code	name	name_ru
    1	HBIOS	human biosynthetic	человеческий биосинтетический
    2	HSEMI	human semisynthetic	человеческий полусинтетический
    3	PORK	pork	свиной
    4	BEEF	beef	говяжий
    5	BEPO	beef-pork	говяже-свиной
     */
    @Column(name = "code", index = true, unique = true)
    public String code;

    @Column(name = "name", notNull = true )
    public String name;
}