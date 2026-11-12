import java.sql.*;

void main() {

    Scanner sc = new Scanner(System.in);
    String url ="jdbc:mysql://localhost:3306/archeologie";
    String user ="root";
    String password ="";

    try(Connection conn = DriverManager.getConnection(url,user,password);) {
        System.out.println("Připojení bylo úspěšné");
        System.out.println("-------------------------");
        System.out.println("");



// Ukol 2
        System.out.println("Seznam nálezů:");
        System.out.println("");
        String dotazStariSelect = "SELECT Nazev, Material, Stari FROM nalezy ORDER BY stari DESC";

        try(PreparedStatement pst = conn.prepareStatement(dotazStariSelect)) {
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString("Nazev")+" | "+rs.getString("Material")+" | "+rs.getString("Stari"));
            }

        }
        System.out.println("");




// Ukol 3
        System.out.print("Zadej materiál:");
        String mat = sc.nextLine();
        System.out.print("Zadej minimální hodnotu:");
        int min = sc.nextInt();
        sc.nextLine();

        String dotazFiltrSelect = "SELECT Nazev, Hodnota FROM nalezy WHERE Material = ? AND Hodnota >= ?";
        boolean nalezeno = false;

        try(PreparedStatement pst = conn.prepareStatement(dotazFiltrSelect)) {
            pst.setString(1, mat);
            pst.setInt(2, min);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                nalezeno = true;
                System.out.println(rs.getString("Nazev")+" | "+rs.getString("Hodnota"));
            }

            if(!nalezeno) {
                System.out.println("Požadavkům neodpovídá žádný předmět.");
            }
        }
        System.out.println("");





// Ukol 4
        System.out.println("Zadej stát: ");
        String stat = sc.nextLine();

        String dotazStatSelect = "SELECT n.Nazev, l.Nazev, n.Stari"+
                        "JOIN lokality l ON n.Id_lokality = l.id "+
                        "WHERE l.stat = ?";

        try(PreparedStatement pst = conn.prepareStatement(dotazStatSelect)) {
            pst.setString(1, stat);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                String nazevLokality = rs.getString("l.Nazev");
                String nazevPredmetu = rs.getString("n.Nazev");
                String stari = rs.getString("n.Stari");
                System.out.println(nazevLokality+" "+nazevPredmetu+" "+stari);
            }
        }


        System.out.println("");




// Ukol 5
        String dotazPocetSelect = "SELECT Material FROM nalezy";

        int zlato = 0;
        int bronzo = 0;
        int keramika = 0;
        int stribro = 0;
        int zelezo = 0;
        int kamen = 0;

        try(PreparedStatement pst = conn.prepareStatement(dotazPocetSelect)) {
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                switch (rs.getString("Material")) {
                    case "zlato": zlato ++; break;
                    case "bronz": bronzo ++; break;
                    case "kámen": kamen++; break;
                    case "stříbro": stribro++; break;
                    case "železo": zelezo++; break;
                }
            }
        }
        System.out.println("Počet materiálů");
        System.out.println("--------------------");
        System.out.println("Zlato: "+zlato);
        System.out.println("Bronzo: "+bronzo);
        System.out.println("Kamen: "+kamen);
        System.out.println("Stribo: "+stribro);
        System.out.println("Zelezo: "+zelezo);

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
