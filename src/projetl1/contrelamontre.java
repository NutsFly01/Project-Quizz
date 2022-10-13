public static void Duelmodejeu(){
                int y=0;
                int z=0;
                int b=0;
                int p=0;
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
                }while(z+b+p+y == 10);
                System.out.println("Fin du jeu");
                System.out.println("Vous avez répondu bon à " +z+ "  questions");
                
                
                
                
}

