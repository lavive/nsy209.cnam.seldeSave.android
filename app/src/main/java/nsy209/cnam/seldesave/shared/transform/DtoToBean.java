package nsy209.cnam.seldesave.shared.transform;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.bean.AssociationBean;
import nsy209.cnam.seldesave.bean.CategoryBean;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.bean.Person;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.bean.helper.EnumSupplyDemand;
import nsy209.cnam.seldesave.dao.DaoFactory;
import nsy209.cnam.seldesave.shared.dto.AssociationDto;
import nsy209.cnam.seldesave.shared.dto.CategoriesDto;
import nsy209.cnam.seldesave.shared.dto.CategoryDto;
import nsy209.cnam.seldesave.shared.dto.MemberDto;
import nsy209.cnam.seldesave.shared.dto.MembersDto;
import nsy209.cnam.seldesave.shared.dto.NotificationDto;
import nsy209.cnam.seldesave.shared.dto.NotificationsDto;
import nsy209.cnam.seldesave.shared.dto.PersonDto;
import nsy209.cnam.seldesave.shared.dto.SuppliesDemandsDto;
import nsy209.cnam.seldesave.shared.dto.SupplyDemandDto;
import nsy209.cnam.seldesave.shared.dto.TransactionDto;
import nsy209.cnam.seldesave.shared.dto.TransactionsDto;
import nsy209.cnam.seldesave.shared.dto.WealthSheetDto;

/**
 * Created by lavive on 21/09/17.
 */

public class DtoToBean {

    public static Person personDtoToBean(PersonDto personDto){
        Person person = new Person();

        person.setRemote_id(personDto.getId());
        person.setName(personDto.getName());
        person.setAddress(personDto.getAddress());
        person.setPostalCode(personDto.getPostalCode());
        person.setTown(personDto.getTown());
        person.setEmail(personDto.getEmail());
        person.setCellNumber(personDto.getCellNumber());
        person.setPhoneNumber(personDto.getPhoneNumber());

        return person;
    }

    public static AssociationBean associationDtoToBean(AssociationDto associationDto){
        AssociationBean associationBean = new AssociationBean();

        associationBean.setRemote_id(associationDto.getId());
        associationBean.setName(associationDto.getName());
        associationBean.setAddress(associationDto.getAddress());
        associationBean.setPostalCode(associationDto.getPostalCode());
        associationBean.setTown(associationDto.getTown());
        associationBean.setEmail(associationDto.getEmail());
        associationBean.setCellNumber(associationDto.getCellNumber());
        associationBean.setPhoneNumber(associationDto.getPhoneNumber());
        associationBean.setWebSite(associationDto.getWebsite());

        return associationBean;
    }

    public static CategoryBean categoryDtoToBean(CategoryDto categoryDto){
        CategoryBean categoryBean = new CategoryBean();

        categoryBean.setRemote_id(categoryDto.getId());
        categoryBean.setCategory(categoryDto.getName());
        categoryBean.setActive(true);

        return categoryBean;
    }

    public static MemberBean memberDtoToBean(MemberDto memberDto){
        MemberBean memberBean = new MemberBean();

        memberBean.setRemote_id(memberDto.getId());
        memberBean.setName(memberDto.getName());
        memberBean.setAddress(memberDto.getAddress());
        memberBean.setPostalCode(memberDto.getPostalCode());
        memberBean.setTown(memberDto.getTown());
        memberBean.setEmail(memberDto.getEmail());
        memberBean.setCellNumber(memberDto.getCellNumber());
        memberBean.setPhoneNumber(memberDto.getPhoneNumber());
        memberBean.setForname(memberDto.getForname());
        memberBean.setActive(true);

        return memberBean;
    }

    public static MyProfileBean myProfileDtoToBean(MemberDto memberDto){
        MyProfileBean myProfileBean = new MyProfileBean();

        myProfileBean.setRemote_id(memberDto.getId());
        myProfileBean.setName(memberDto.getName());
        myProfileBean.setAddress(memberDto.getAddress());
        myProfileBean.setPostalCode(memberDto.getPostalCode());
        myProfileBean.setTown(memberDto.getTown());
        myProfileBean.setEmail(memberDto.getEmail());
        myProfileBean.setCellNumber(memberDto.getCellNumber());
        myProfileBean.setPhoneNumber(memberDto.getPhoneNumber());
        myProfileBean.setForname(memberDto.getForname());
        myProfileBean.setMobileId(memberDto.getMobileId());

        return myProfileBean;
    }

    public static NotificationBean notificationDtoToBean(NotificationDto notificationDto){
        NotificationBean notificationBean = new NotificationBean();

        notificationBean.setRemote_id(notificationDto.getId());
        notificationBean.setTitle(notificationDto.getTitle());
        notificationBean.setText(notificationDto.getText());
        notificationBean.setCategory(notificationDto.getTopic().getCategory());
        notificationBean.setPersonOriginId(notificationDto.getTopic().getPersonOriginEvent().getId());
        notificationBean.setRead(false);

        return notificationBean;
    }

    public static SupplyDemandBean supplyDemandDtoToBean(SupplyDemandDto supplyDemandDto){
        SupplyDemandBean supplyDemandBean = new SupplyDemandBean();

        supplyDemandBean.setRemote_id(supplyDemandDto.getId());
        supplyDemandBean.setType(EnumSupplyDemand.getByWording(supplyDemandDto.getType()));
        supplyDemandBean.setCategory(supplyDemandDto.getCategory());
        supplyDemandBean.setTitle(supplyDemandDto.getTitle());
        supplyDemandBean.setRemote_id(supplyDemandDto.getId());
        supplyDemandBean.setMember(memberDtoToBean(supplyDemandDto.getMember()));
        supplyDemandBean.setChecked(true);
        supplyDemandBean.setActive(true);

        return supplyDemandBean;
    }

    public static TransactionBean transactionDtoToBean(TransactionDto transactionDto, DaoFactory daoFactory){
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setCreditor(daoFactory.getMemberDao().getMemberByRemoteId(transactionDto.getCreditorMemberId()));
        transactionBean.setDebtor(daoFactory.getMemberDao().getMemberByRemoteId(transactionDto.getDebtorMemberId()));
        transactionBean.setSupplyDemand(daoFactory.getSupplyDemandDao().getSupplyDemandByRemoteId(transactionDto.getSupplyDemandId()));
        transactionBean.setAmount(new BigDecimal(transactionDto.getAmount()));
        transactionBean.setRemote_id(transactionDto.getId());
        transactionBean.setActive(true);

        return transactionBean;
    }

    public static WealthSheetBean wealthSheetDtoToBean(WealthSheetDto wealthSheetDto){
        WealthSheetBean wealthSheetBean = new WealthSheetBean();

        wealthSheetBean.setRemote_id(wealthSheetDto.getId());
        wealthSheetBean.setInitialAccount(new BigDecimal(wealthSheetDto.getInitialAccount()));
        wealthSheetBean.setFinalAccount(new BigDecimal(wealthSheetDto.getFinalAccount()));
        wealthSheetBean.setTransactions(new ArrayList<TransactionBean>());

        return wealthSheetBean;
    }

    public static List<CategoryBean>  categoryDtoToBean(CategoriesDto categoriesDto){
        List<CategoryBean> categoriesBean = new ArrayList<CategoryBean>();

        for(CategoryDto categoryDto:categoriesDto.getCategories()){
            categoriesBean.add(categoryDtoToBean(categoryDto));
        }

        return categoriesBean;
    }

    public static List<MemberBean>  memberDtoToBean(MembersDto membersDto){
        List<MemberBean> membersBean = new ArrayList<MemberBean>();

        for(MemberDto memberDto:membersDto.getMembers()){
            membersBean.add(memberDtoToBean(memberDto));
        }

        return membersBean;
    }

    public static List<NotificationBean>  notificationDtoToBean(NotificationsDto notificationsDto){
        List<NotificationBean> notificationsbean = new ArrayList<NotificationBean>();

        for(NotificationDto notificationDto:notificationsDto.getNotifications()){
            notificationsbean.add(notificationDtoToBean(notificationDto));
        }

        return notificationsbean;
    }

    public static List<SupplyDemandBean>  supplyDemandDtoToBean(SuppliesDemandsDto suppliesDemandsDto){
        List<SupplyDemandBean> suppliesDemandsBean = new ArrayList<SupplyDemandBean>();

        for(SupplyDemandDto supplyDemandDto:suppliesDemandsDto.getSuppliesDemands()){
            suppliesDemandsBean.add(supplyDemandDtoToBean(supplyDemandDto));
        }

        return suppliesDemandsBean;
    }

    public static List<TransactionBean>  transactionDtoToBean(TransactionsDto transactionsDto, DaoFactory daoFactory){
        List<TransactionBean> transactionsBean = new ArrayList<TransactionBean>();

        for(TransactionDto transactionDto:transactionsDto.getTransactions()){
            transactionsBean.add(transactionDtoToBean(transactionDto,daoFactory));
        }

        return transactionsBean;
    }

    public static List<Long>  transactionDtoToBean(TransactionsDto transactionsDto){
        List<Long> transactionRemoteIds = new ArrayList<Long>();

        for(TransactionDto transactionDto:transactionsDto.getTransactions()){
            transactionRemoteIds.add(transactionDto.getId());
        }

        return transactionRemoteIds;
    }
}
