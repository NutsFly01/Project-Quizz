package projetl1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class juste_le_jeu2 {
	
	static Scanner sc3= new Scanner(System.in);
	
	
	public static Connection connecterBD() {
		try{
			Class.forName("com.mysql.jdbc.Driver");//Le chemin du driver pour mysql
			//System.out.println("Driver ok");
			String url="jdbc:mysql://localhost:3306/test";//Le chemin de la basse de données
			String user="root";// Identifiant
			String password="root";// Mot de passe
			Connection cnx=DriverManager.getConnection(url,user,password); // La commande qui permet de se connecter
			//System.out.println("Connection bien établie");
			/* Création de l'objet gérant les requêtes */
			Statement statement = cnx.createStatement();//Elle permet de gérer les requêtes 
			return cnx;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
		//
	//
	public static String reponse(int a,String r,String r1,String r2,String r3,String r4){
		int b;
		
			Scanner sc2 = new Scanner (System.in);
			if(a==1){
				System.out.println("Entrez votre reponse ?");
				r+=sc2.nextLine();
		}
		if(a==2){
			System.out.println("les proposition suivant :");
			System.out.print("1: "+r1); System.out.println("\t\t2: "+r2);
			System.out.print("3: "+r3); System.out.println("\t\t4: "+r4);
			System.out.println("Entrez votre reponse ? (1,2,3 ou 4)");
			//System.out.println("9: Revoir la question");
			b=sc2.nextInt();
		if(b==1){r=r1;}
		if(b==2){r=r2;}
		if(b==3){r=r3;}
		if(b==4){r=r4;}
		}
		return r;
	}
		//
	//
	public static void modesurvie(int id) throws SQLException{
		int jeu =1;
		int y=0;
		int z=0;
		Connection cnx=connecterBD();
		Statement statement = cnx.createStatement();// Gérer les requêtes.
		
		ResultSet resultat1=statement.executeQuery
				("SELECT MAX(id) FROM user ;");

		int e;
		while ( resultat1.next() ) {
		e = resultat1.getInt( "MAX(id)" );
		id=e;
		}
		
		do{
			int a= (int)(Math.random()*id+1);
		if(y!=9){
		/* recherche des element dans la base de donnée (questiion et reponse)*/
		ResultSet resultat=statement.executeQuery
				("SELECT Question,Reponse1,Reponse2,Reponse3,Reponse4,BReponse FROM hisgeo WHERE id="+a+";");
		
		String Q="";
		String R1="";
		String R2="";
		String R3="";
		String R4="";
		String BR="";
		
		while ( resultat.next() ) {
			
			Q = resultat.getString( "Question" );
        	R1 = resultat.getString( "Reponse1" );
        	R2 = resultat.getString( "Reponse2" );
        	R3 = resultat.getString( "Reponse3" );
        	R4 = resultat.getString( "Reponse4" );
        	BR = resultat.getString( "BReponse" );
		}
		//
		System.out.println(Q);
		System.out.println("Comment voulez vous repondre ? 1 pour à l'aveugle ou 2 pour avoir des propositions");
		Scanner sc = new Scanner (System.in);
		Scanner sc1 = new Scanner (System.in);
		
		int ab = sc.nextInt();
		
		String r="";
		String rj=reponse(ab,r,R1,R2,R3,R4);
		System.out.println("Vous avez répondu : "+rj);
		if(comparer(rj,BR)){
			System.out.println("Vous aviez la bonne reponse qui était "+BR+" !!! Bien joué");
			z++;
		}
		else{
			System.out.println("Dommage ! La bonne reponse était : "+BR);
			y=9;
		}
		}
		}while(y!=9);
		score(cnx,id,jeu,z,z+1);
		System.out.println("Fin du jeu");
		System.out.println("Vous avez répondu bon à " +z+ "  questions");
		
		
		
	}
		
		//	
	//
	public static boolean comparer(String s1,String s2){
		Collator collator = Collator.getInstance(Locale.FRENCH);
		collator.setStrength(Collator.PRIMARY);// Primary permet d'ignorer les fautes d'accents et de majuscules
		int comparaison = collator.compare(s1,s2);
		
		if (comparaison==0)
			return true;//Si les deux sont identiques 
		else 
			return false;//Si elles sont différentes
		
		
	}
		//
	//
	public static void inscription() throws SQLException{
		Connection cnx=connecterBD();
		Statement statement = cnx.createStatement();// Gérer les requêtes.
		String user;
		String Mail;
		String Mdp;
		Scanner sc = new Scanner(System.in);
		do{
		System.out.println("Entrez le nom de l'utilisateur: ");
		user = sc.nextLine();
		if( user.length()>10 )
			System.out.println("Erreur votre Nom est trop long !");
		}while( user.length()>10 );
		
		do{
			System.out.println("Entrez votre adresse mail: ");
		Mail = sc.nextLine();
			
		}while( Mail.length()<8 );
		
		do{
			System.out.println("entrez votre mot de passe: ");
		Mdp = sc.nextLine();
		if( Mail.length()<8 )
			System.out.println("Erreur votre Mdp est trop court !");
		}while( Mdp.length()<8 );
		int id = 0;
		//
		ResultSet resultat=statement.executeQuery
				("SELECT MAX(id) FROM user ;");
		
		int e;
		while ( resultat.next() ) {
		e = resultat.getInt( "MAX(id)" );
		id=e;
		}
		 
		//
		int statut = statement.executeUpdate
				( "INSERT INTO user (ID,User,MDP,Email,Ndqr,Nbr) VALUE ('"+(id+1)+"','"+user+"','"+Mdp+"','"+Mail+"',0,0);");
	System.out.println("Vous avez etes inscrit !");	
	}
		//
	//
	public static int ConUser() throws SQLException{
		Connection cnx=connecterBD();
		Statement statement = cnx.createStatement();// Gérer les requêtes.
		Scanner sc = new Scanner(System.in);
		System.out.println("entrez un nom d'utilisateur : ");
		String User=sc.nextLine();
		System.out.println("entrez le mot de passe : ");
		String Mdp = sc.nextLine();
		//recuperation du dernier id pour le max
		int id = 0;
		ResultSet resultat=statement.executeQuery
				("SELECT MAX(id) FROM user ;");
		int e;
		while ( resultat.next() ) {
		e = resultat.getInt( "MAX(id)" );
		id=e;
		}
		//
		int id1=0;
		String User1="";
		String Mdp1="";
		
		for(int i = 1;i<id;i++){
			ResultSet resultat1=statement.executeQuery
					("SELECT ID,User,Mdp FROM user where User='"+User+"';");

			
			while ( resultat1.next() ) {
				 User1 = resultat1.getString( "User" );
	             Mdp1 = resultat1.getString( "Mdp" );
	             id1= resultat1.getInt( "ID" );
	                      }//while 		
			}//for
		if(User.equals(User1)){
	       	 if(Mdp.equals(Mdp1)){
	       		 System.out.println("Vous etes Connecter");}
	         else{
	       		 System.out.println("Mot de passe faux");}
       	 }//if
		else{
			System.out.println("Vous n'etes pas inscrit !");
		}

		return id1; 
		}
			//
	public static void affiscore (int id) throws SQLException{
		Connection cnx=connecterBD();
		Statement statement = cnx.createStatement();
		
		ResultSet resultat=statement.executeQuery
				("SELECT User,Ndqr,Nbr,msurvie FROM user where ID="+id+";");
		int total = 0;
		int br = 0;
		int msurvie=0;
		String User ="";
		//int msurvie =0;
		while ( resultat.next() ) {
			User = resultat.getString( "User" );
			total = resultat.getInt( "Nbr" );
			br = resultat.getInt( "Nq" );
			msurvie = resultat.getInt( "msurvie" ); 
			//mmontre = resultat.getInt( "mmontre" );
			
		}
		System.out.println("**********Les Scores de "+User+"**********");
		System.out.println("Vous avec reussi à repondre bon à "+total+" sur un total de "+br+" !");
		System.out.println("Dans le mode survie le meilleur score obtenu est de : "+msurvie);
		//System.out.println("En 60 seconde, vous avez repondu bon a :"+mmontre+" question(s)");
		
	}
		//
	public static void score(Connection cnx,int id,int jeu,int nq,int nbr) throws SQLException{
		//id est donnée toute seul, nq(nombre question posé) et nbr(nombre de bonne reponse) sont donné par le jeu, 
		
		//Connection cnx=connecterBD();
		Statement statement = cnx.createStatement();
		
		int nq1 = 0;
		int nbr1= 0;
		int msurvie= 0;
		//recupere le score du joueur
		ResultSet resultat=statement.executeQuery
				("SELECT Nq,Nbr,msurvie FROM user where ID="+id+" ;");
		
		while ( resultat.next() ) {
			nq1 = resultat.getInt( "Nq" );
			nbr1 = resultat.getInt( "Nbr" );
			msurvie = resultat.getInt( "msurvie" );
			}
			int a=0;
			int b=0;
			a=nq1+nq;
			b=nbr+nbr1;
			
		//mettre a jour la bd
		int statut = statement.executeUpdate("UPDATE user SET Nq="+a+" where ID="+id+";");
		int statut1 = statement.executeUpdate("UPDATE user SET Nbr="+b+" WHERE ID="+id+";");
		int statut2;
		if(jeu==1 && nbr>msurvie) 
			statut2 = statement.executeUpdate("UPDATE user SET msurvie="+nbr+" where ID="+id+";");
	}
	//
	
	//pour la fonction qui fait appelle au jeux

	public static void modecontrelamontre1() throws SQLException{
	                        
	                        final Timer timer = new Timer();
	                         boolean temps = true;
	                        
	                         TimerTask task =new TimerTask(){
	                             volatile int sp = 60 ;        
	                                   public void run(){
	                                                 if (  sp > 0) {
	                                                      sp  =  sp  - 1;
	                                                      //System.out.println("je "+sp);
	                                                      }
	                                                
	                                                 else {
	                                                        System.out.println("Terminé!");
	                                                        timer.cancel();
	                                                      
	                                                      
	                                                      }
	                                              }
	                                        };
	                        
	                        TimerTask task1 =new TimerTask(){
	                    long startTime = System.currentTimeMillis()+60000;
	                        Scanner sc = new Scanner(System.in);
	                public void run(){
	                          while(System.currentTimeMillis() < startTime ){
	                                  try {
	                                        modecontrelamontre();
	                                } catch (SQLException e) {
	                                        // TODO Auto-generated catch block
	                                        e.printStackTrace();
	                                }
	                                                                        }
	                                                                
	                                                        }
	                                                };
	                        timer.scheduleAtFixedRate(task,0,1000);        
	                        timer.scheduleAtFixedRate(task1,0,60000);
	                                
	                          }
	                        
	// le jeux

	public static void modecontrelamontre() throws SQLException{
	                        int y=0;
	                        int z=0;
	                        long startTime = System.currentTimeMillis()+60000;
	                        do{
	                                int a= (int)(Math.random()*7+1);
	                                Connection cnx=connecterBD();
	                        if(System.currentTimeMillis() < startTime ){
	                        Statement statement = cnx.createStatement();
	                        
	                        ResultSet resultat=statement.executeQuery
	                                ("SELECT Question,Reponse1,Reponse2,Reponse3,Reponse4,BReponse FROM hisgeo WHERE id="+a+";");
	                        
	                        String Q="";
	                        String R1="";
	                        String R2="";
	                        String R3="";
	                        String R4="";
	                        String BR="";
	                        
	                        while ( resultat.next() ) {
	                                
	                                Q = resultat.getString( "Question" );
	                    R1 = resultat.getString( "Reponse1" );
	                    R2 = resultat.getString( "Reponse2" );
	                    R3 = resultat.getString( "Reponse3" );
	                    R4 = resultat.getString( "Reponse4" );
	                    BR = resultat.getString( "BReponse" );
	                        }
	                        
	                        System.out.println(Q);
	                        System.out.println("Comment voulez vous repondre ? 1 pour à l'aveugle ou 2 pour avoir des propositions");
	                        Scanner sc = new Scanner (System.in);
	                        Scanner sc1 = new Scanner (System.in);
	                        
	                        int ab = sc.nextInt();
	                        
	                        String r="";
	                        String rj=reponse(ab,r,R1,R2,R3,R4);
	                        System.out.println("Vous avez répondu : "+rj);
	                        if(comparer(rj,BR)){
	                        System.out.println("Vous aviez la bonne reponse qui était "+BR+" !!! Bien joué");
	                        z++;
	                        }
	                        else{
	                        System.out.println("Dommage ! La bonne reponse était : "+BR);
	                        y++;
	                        }
	                        }
	                        }while(System.currentTimeMillis() < startTime );
	                        System.out.println("Fin du jeu");
	                        System.out.println("Vous avez répondu bon à " +z+ " questions et mouvais à "+y);
	                        
	                        }
	                        //
	
	
	public static void Duelmodejeu() throws SQLException{
        int y=0;
        int z=0;
        int b=0;
        int p=0;
        int v=0;
        int joueur = 1;
        
        
        do{
                if(joueur == 1)
                {
                        System.out.println("joueur "+joueur+ "a vous de jouer");
                int a= (int)(Math.random()*7+1);
                //System.out.println(a);
                Connection cnx=connecterBD();
                //System.out.println("Continuer à jouer ? Tapez 9 si vous voulez arrêter sinon tapez 1");
                //y = sc3.nextInt();
        if(y!=9){
        Statement statement = cnx.createStatement();// Gérer les requêtes.
        /* recherche des element dans la base de donnée (questiion et reponse)*/
        ResultSet resultat=statement.executeQuery
                        ("SELECT Question,Reponse1,Reponse2,Reponse3,Reponse4,BReponse FROM hisgeo WHERE id="+a+";");
        
        String Q="";
        String R1="";
        String R2="";
        String R3="";
        String R4="";
        String BR="";
        
        while ( resultat.next() ) {
                
                 Q = resultat.getString( "Question" );
     R1 = resultat.getString( "Reponse1" );
     R2 = resultat.getString( "Reponse2" );
     R3 = resultat.getString( "Reponse3" );
    R4 = resultat.getString( "Reponse4" );
     BR = resultat.getString( "BReponse" );
        }
        //
        System.out.println(Q);
        System.out.println("Comment voulez vous repondre ? 1 pour à l'aveugle ou 2 pour avoir des propositions");
        Scanner sc = new Scanner (System.in);
        Scanner sc1 = new Scanner (System.in);
        
        int ab = sc.nextInt();
        
        String r="";
        String rj=reponse(ab,r,R1,R2,R3,R4);
        System.out.println("Vous avez répondu : "+rj);
        if(comparer(rj,BR)){
        System.out.println("Vous aviez la bonne reponse qui était "+BR+" !!! Bien joué");
        z++;
        joueur++;
        }
        else{
        System.out.println("Dommage ! La bonne reponse était : "+BR);
        y++;
        joueur++;
        }
        }if(joueur == 2 ){
                System.out.println("joueur "+joueur+ "a vous de jouer");
                a= (int)(Math.random()*7+1);
                //System.out.println(a);
                //Connection cnx=connecterBD();
                //System.out.println("Continuer à jouer ? Tapez 9 si vous voulez arrêter sinon tapez 1");
                //y = sc3.nextInt();
        if(y!=9){
        Statement statement = cnx.createStatement();// Gérer les requêtes.
        /* recherche des element dans la base de donnée (questiion et reponse)*/
        ResultSet resultat1=statement.executeQuery
                        ("SELECT Question,Reponse1,Reponse2,Reponse3,Reponse4,BReponse FROM hisgeo WHERE id="+a+";");
        
        String Q="";
        String R1="";
        String R2="";
        String R3="";
        String R4="";
        String BR="";
        
        while ( resultat1.next() ) {
                
                 Q = resultat1.getString( "Question" );
     R1 = resultat1.getString( "Reponse1" );
     R2 = resultat1.getString( "Reponse2" );
     R3 = resultat1.getString( "Reponse3" );
    R4 = resultat1.getString( "Reponse4" );
     BR = resultat1.getString( "BReponse" );
        }
        //
        System.out.println(Q);
        System.out.println("Comment voulez vous repondre ? 1 pour à l'aveugle ou 2 pour avoir des propositions");
        Scanner sc = new Scanner (System.in);
        Scanner sc1 = new Scanner (System.in);
        
        int ab = sc.nextInt();
        
        String r="";
        String rj=reponse(ab,r,R1,R2,R3,R4);
        System.out.println("Vous avez répondu : "+rj);
        if(comparer(rj,BR)){
        System.out.println("Vous aviez la bonne reponse qui était "+BR+" !!! Bien joué");
        b++;
        joueur--;
        }
        else{
        System.out.println("Dommage ! La bonne reponse était : "+BR);
        p++;
        joueur--;
        }
        }
        }
        }
                v=z+b+p+y;
        }while(v != 10);
        System.out.println("Fin du jeu");
        System.out.println("Vous avez répondu bon à " +z+ "  questions");
        
        
        
        
}


	                

	
	//
	public static void main(String[] args)throws SQLException {
		//modecontrelamontre1();
		Duelmodejeu();
		//int id=1;
		//modesurvie(id);// Il lance le mode survie
		/*
		Scanner sc = new Scanner(System.in);
		System.out.println("vous voulez vous connecter (y/n)");
		char n=sc.nextLine().charAt(0);
		if(n=='y')
			id=ConUser();
		*/
		
		//score(1,1,2,3);
		//ContreLaMontre();
		//inscription();
		
		 
	}
		//
	//
}
