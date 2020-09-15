package MutiSqlTest;

import java.io.File;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MuitSqlExecTest {

    static LinkedList<String> results = new LinkedList<>();

    static int count = 0;

    static int totalSqlConut = 0;

    public static void main(String[] args) throws IOException {

        String[][] SQLlist = getSqlList("sqlfile");

        int[] visitPos = new int[SQLlist.length];

        Execute(visitPos, SQLlist);

        System.out.println("共" + count + "种执行顺序");

    }

    static void Execute(int[] visitPos, String[][] SQLlist) {

        if (results.size() == totalSqlConut) {

            count++;

            System.out.println("情况 " + count + ":");
            for (String s : results) {
                System.out.println(s);
            }
            System.out.println();
            return;
        }

        for (int i = 0; i < SQLlist.length; i++) {

            String exeSql = SQLlist[i][visitPos[i]];

            if (exeSql == null) {
                continue;
            }

            results.add("client" + (i + 1) + ":" + exeSql);

            int tmp = visitPos[i];

            visitPos[i]++;

            Execute(visitPos, SQLlist);

            visitPos[i] = tmp;

            results.removeLast();
        }

    }

    public static String[][] getSqlList(String path) throws IOException {

        File file = new File(path);
        File[] sqlFileList = file.listFiles();

        String[][] sqlLists = new String[sqlFileList.length][];

        for (int i = 0; i < sqlFileList.length; i++) {

            ArrayList<String> list = new ArrayList<>();

            FileInputStream inputStream = new FileInputStream(sqlFileList[i]);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String s = bufferedReader.readLine();
            while (s != null) {
                list.add(s);
                s = bufferedReader.readLine();
            }

            totalSqlConut = totalSqlConut + list.size();

            list.add(null);

            sqlLists[i] = list.toArray(new String[list.size()]);

            inputStream.close();
            bufferedReader.close();

        }

        return sqlLists;
    }
}
