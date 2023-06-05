package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Credit;
import com.example.foryou.DAO.Entities.StateCredit;
import com.example.foryou.DAO.Entities.User;
import com.example.foryou.DAO.Repositories.CreditRepository;
import com.example.foryou.DAO.Repositories.UserRepository;
import com.example.foryou.Services.Interfaces.ICreditService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@AllArgsConstructor
@Service
public class CreditService implements ICreditService {

    private CreditRepository creditRepository;
    private UserRepository userRepository;



    @Override
    public Credit add(Credit c) {
        String username=this.getCurrentUserName();
        User user =this.getUser(username);
        c.setClient(user);
        return creditRepository.save(c);
    }

    @Override
    public Credit edit(Credit u) {
        return creditRepository.save(u);
    }

    @Override
    public List<Credit> selectAll() {
        return creditRepository.findAll();
    }

    @Override
    public Credit selectById(int creditId) {
        return creditRepository.findById(creditId).get();
    }

    @Override
    public void deleteById(int creditId) {
        creditRepository.deleteById(creditId);
    }

    @Override
    public void delete(Credit c) {
        creditRepository.delete(c);
    }

    @Override
    public List<Credit> addAll(List<Credit> list) {
        return creditRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Credit> list) {
        creditRepository.deleteAll(list);
    }

    @Override
    public void deleteAll() {
        creditRepository.deleteAll();
    }

  /*  public static int calculateYearsBetween(Date date1, Date date2) {

        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        return Math.abs(localDate1.getYear() - localDate2.getYear());
    }*/



@Override
    public void Calcul1(Credit c) {
        float amt = 0;
        double x=0;

        float[][] matrice = new float[(c.getNb_years() * 12)+1][4];
        float m=matrice[0][3];
        for (int j = 0; j <=(c.getNb_years() * 12); j++) {
            if (j == 0) {
                matrice[j][0] = c.getAmount();
                matrice[j][1] = (matrice[j][0] * c.getInterestRate()) / 12;//interet
                matrice[j][2] = matrice[j][0] / (c.getNb_years() * 12);//amt
                amt = matrice[j][2];
                matrice[j][3] = amt + matrice[j][1];//mensualite
            }
            else {
                matrice[j][0] = matrice[j - 1][0] - amt;//montant
                matrice[j][1] = (matrice[j][0] * c.getInterestRate()) / 12;//interet
                matrice[j][2] = amt;//amt
                matrice[j][3] = amt + matrice[j][1];//mensualite
            }
        }
        for (int j = 0; j < matrice.length; j++) {
            for (int i = 0; i < 4; i++) {
                if(j== (matrice.length-1)){
                    matrice[j][0]=0;
                    matrice[j][1]=0;
                }
                DecimalFormat df = new DecimalFormat("#.###");
                System.out.print(df.format(matrice[j][i]) + " "+ " ");
            }
            System.out.println();
            //rentabilité
            m=m+matrice[j][3];
        }
    c.setRefundAmount(m);
    c.setRentability((c.getRefundAmount()-c.getAmount())/c.getAmount());
    System.out.println("la rentabilité par crédit est "+c.getRentability());
    creditRepository.save(c);

    /*LocalDate localDate1 = c.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate localDate2 = c.getEndtDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    // Calculez la différence entre les deux objets java.time.LocalDate en années
    long differenceInYears = ChronoUnit.YEARS.between(localDate2, localDate1);

    // Affichez le résultat
    System.out.println("La différence entre les deux dates en années est : " + differenceInYears + " ans.");

    String d = String.valueOf(c.getStartDate());
    String d1 = String.valueOf(c.getEndtDate());
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    try {
        Date date = sdf.parse(d);
        Date date1 = sdf.parse(d1);
        Duration duration = Duration.between(date.toInstant(), date1.toInstant());
        System.out.println(duration);
    }
    catch (ParseException e) {
        e.printStackTrace();
    }*/

    }
    @Override
    public void Calcul2(Credit c){
        float  ms=0;
        float resultat=0;
        float rentabilité=0;
        double x=0;
        int nb=0;
        nb=c.getNb_years();
        float[][] matrice = new float[(nb * 12)+1][4];


        for (int j = 0; j <=(nb * 12); j++) {
            if (j == 0) {

                matrice[j][0] = c.getAmount();//montant
                matrice[j][1] = (matrice[j][0] * c.getInterestRate()) / 12;//interet

                x=Math.pow(1+(c.getInterestRate()/12), (nb)*(-12));
                matrice[j][3] = (float) (matrice[j][0] * (c.getInterestRate()/12)/(1-x));//mensualite
                ms=matrice[j][3];

                matrice[j][2]=ms-matrice[j][1];//amt
            }
            else {
                matrice[j][3] = ms;//mensualite
                matrice[j][0] = matrice[j - 1][0] - matrice[j-1][2];//montant
                matrice[j][1] = (matrice[j][0] * c.getInterestRate()) / 12;//interet
                matrice[j][2] = ms-matrice[j][1];//amt


            }
        }
        for (int j = 0; j < matrice.length; j++) {
            for (int i = 0; i < 4; i++) {
                if(j== (matrice.length-1)){
                    matrice[j][0]=0;
                    matrice[j][1]=0;
                }
                DecimalFormat df = new DecimalFormat("#.###");
                System.out.print(df.format(matrice[j][i]) + " "+ " ");
            }
            System.out.println();

        }
       //rentabilité
       c.setRefundAmount(matrice[0][3]*12*nb);
       c.setRentability((c.getRefundAmount()-c.getAmount())/c.getAmount());
        System.out.println("la rentabilité par crédit"+c.getRentability());
        creditRepository.save(c);

    }


    @Override
    @Scheduled(cron ="0 0 9 31 12 *" ) //le 31/12 de n'importe quelle année à 9:00:00
    public void Rentabilité()
    {  int nb_credits=0;
        float rentabilite=0;
        nb_credits=(int)creditRepository.count();
        System.out.println(nb_credits);
        rentabilite=(float)creditRepository.SommeRentabilite()/nb_credits;
        System.out.print("la rentabilite globale est: "+rentabilite);

    }

    @Override
    public List<Credit> Scoring() {

        return creditRepository.selectBySalary();
    }

    @Override
    public void StatusCredit(){
    List<Credit> creditList=creditRepository.selectBySalary();
     for (Credit credit: creditList){
        credit.setStatus(StateCredit.ACCEPTED);
        creditRepository.save(credit);
     }
    }
    @Override
    public float Profit(String type, String region)
    {
        return creditRepository.selectByTypeAndRegion(type,region);

    }
    @Override
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }



}
