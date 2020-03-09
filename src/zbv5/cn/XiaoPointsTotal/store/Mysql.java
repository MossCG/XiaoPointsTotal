package zbv5.cn.XiaoPointsTotal.store;

import org.bukkit.entity.Player;
import zbv5.cn.XiaoPointsTotal.lang.Lang;
import zbv5.cn.XiaoPointsTotal.util.DataUtil;
import zbv5.cn.XiaoPointsTotal.util.DateUtil;
import zbv5.cn.XiaoPointsTotal.util.FileUtil;
import zbv5.cn.XiaoPointsTotal.util.PrintUtil;

import java.sql.*;
import java.util.List;

public class Mysql
{
    public static void createTable()
    {
        try {
            Connection conn = DriverManager.getConnection(FileUtil.config.getString("Mysql.Url"), FileUtil.config.getString("Mysql.User"), FileUtil.config.getString("Mysql.PassWord"));
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            st.execute("CREATE TABLE IF NOT EXISTS `"+table+"` (`name` VARCHAR(40) PRIMARY KEY, `uuid`  VARCHAR(40), `total_all`  VARCHAR(40), `total_month`  VARCHAR(40), `total_week`  VARCHAR(40), `total_day`  VARCHAR(40), `total_first`  VARCHAR(40) , `date_firstday`  VARCHAR(40), `date_lastday`  VARCHAR(40)     );");
            PrintUtil.PrintConsole("&3Mysql&7（table:"+table+") &a连接成功!");
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3Mysql &c数据库创表出现问题!");
            e.printStackTrace();
        }
    }

    public static void getPlayerData(Player p)
    {
        int all = 0;
        int month = 0;
        int week = 0;
        int day = 0;
        int first = 0;
        String FirstDay = Lang.NoTotal;
        String LastDay = Lang.NoTotal;
        try {
            Connection conn = DriverManager.getConnection(FileUtil.config.getString("Mysql.Url"), FileUtil.config.getString("Mysql.User"), FileUtil.config.getString("Mysql.PassWord"));
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" + p.getName() + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next())
            {
                first = rs.getInt("total_first");
                FirstDay = rs.getString("date_firstday");
                LastDay = rs.getString("date_lastday");
                String[] dates = LastDay.split("/");
                //总累计
                all = rs.getInt("total_all");
                //当天
                if(DateUtil.getDate("yyyy/MM/dd").equals(LastDay))
                {
                    day = rs.getInt("total_day");
                }
                //判断周
                List<String> WeekList = DateUtil.getWeekDate();
                if(WeekList.contains(LastDay))
                {
                    week =  rs.getInt("total_week");
                }
                //本年
                if((DateUtil.getDate("yyyy").equals(dates[0]) && (DateUtil.getDate("MM").equals(dates[1]))))
                {
                    month = rs.getInt("total_month");
                }
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3玩家数据 &c查询出现问题!");
            e.printStackTrace();
        }
        DataUtil.setCache(p,all,month,week,day,first,FirstDay,LastDay);
    }

    public static void addPlayerData(Player p,int points)
    {
        try {
            Connection conn = DriverManager.getConnection(FileUtil.config.getString("Mysql.Url"), FileUtil.config.getString("Mysql.User"), FileUtil.config.getString("Mysql.PassWord"));
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" + p.getName() + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (!rs.next())
            {
                st.execute("INSERT INTO "+table+" VALUES ('" + p.getName() + "', '" + p.getUniqueId() + "', '"+points+"', '"+points+"', '"+points+"', '"+points+"', '"+points+"', '"+ DateUtil.getDate(Lang.FirstTotalFormat)+"', '"+DateUtil.getDate("yyyy/MM/dd")+"')");
                rs.close();
                st.close();
                conn.close();
                DataUtil.setCache(p,points,points,points,points,points,DateUtil.getDate(Lang.FirstTotalFormat),DateUtil.getDate("yyyy/MM/dd"));
                return;
            }
            int all = Integer.parseInt(DataUtil.getPlayerDate(p,"total","all"));
            int month = Integer.parseInt(DataUtil.getPlayerDate(p,"total","month"));
            int week = Integer.parseInt(DataUtil.getPlayerDate(p,"total","week"));
            int day = Integer.parseInt(DataUtil.getPlayerDate(p,"total","day"));
            String LastDay = DataUtil.getPlayerDate(p,"date","LastDay");
            String[] dates = LastDay.split("/");

            all = all+points;
            //当天
            if(DateUtil.getDate("yyyy/MM/dd").equals(LastDay))
            {
                day = day+points;
            } else {
                LastDay = DateUtil.getDate("yyyy/MM/dd");
                day = points;
            }
            //判断周
            List<String> WeekList = DateUtil.getWeekDate();
            if(WeekList.contains(LastDay))
            {
                week =  week + points;
            } else {
                week =  points;
            }
            //本年
            if((DateUtil.getDate("yyyy").equals(dates[0])) && (DateUtil.getDate("MM").equals(dates[1])))
            {
                month = month + points;
            }  else {
                month = points;
            }
            st.executeUpdate("UPDATE "+table+" set name= '" + p.getName() + "' , total_all= '" + all + "', total_month='" + month + "' , total_week='" + week + "', total_day='" + day + "' , date_lastday='" + LastDay + "'WHERE name='" + p.getName() + "'");
            DataUtil.setCache(p,all,month,week,day,Integer.parseInt(DataUtil.getPlayerDate(p,"total","first")),DataUtil.getPlayerDate(p,"date","FirstDay"),LastDay);
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3玩家数据 &c修改出现问题!");
            e.printStackTrace();
        }
    }
}