package nsy209.cnam.seldesave.shared.transform;

import java.util.ArrayList;
import java.util.List;

import nsy209.cnam.seldesave.activity.utils.ActivityConstant;
import nsy209.cnam.seldesave.bean.MemberBean;
import nsy209.cnam.seldesave.bean.MyProfileBean;
import nsy209.cnam.seldesave.bean.NotificationBean;
import nsy209.cnam.seldesave.bean.SupplyDemandBean;
import nsy209.cnam.seldesave.bean.TransactionBean;
import nsy209.cnam.seldesave.bean.WealthSheetBean;
import nsy209.cnam.seldesave.shared.dto.MemberDto;
import nsy209.cnam.seldesave.shared.dto.MembersDto;
import nsy209.cnam.seldesave.shared.dto.NotificationDto;
import nsy209.cnam.seldesave.shared.dto.NotificationTopicDto;
import nsy209.cnam.seldesave.shared.dto.NotificationsDto;
import nsy209.cnam.seldesave.shared.dto.SupplyDemandDto;
import nsy209.cnam.seldesave.shared.dto.TransactionDto;
import nsy209.cnam.seldesave.shared.dto.WealthSheetDto;

/**
 * Created by lavive on 21/09/17.
 */

public class BeanToDto {
    public static MemberDto myProfileBeanToDto(MyProfileBean myProfileBean){
        MemberDto memberDto = new MemberDto();

        WealthSheetDto wealthSheetDto = new WealthSheetDto();
        wealthSheetDto.setId(ActivityConstant.BAD_ID);
        wealthSheetDto.setInitialAccount("0");
        wealthSheetDto.setFinalAccount("0");

        memberDto.setId(myProfileBean.getRemote_id());
        memberDto.setMobileId(myProfileBean.getMobileId());
        memberDto.setName(myProfileBean.getName());
        memberDto.setAddress(myProfileBean.getAddress());
        memberDto.setPostalCode(myProfileBean.getPostalCode());
        memberDto.setTown(myProfileBean.getTown());
        memberDto.setEmail(myProfileBean.getEmail());
        memberDto.setCellNumber(myProfileBean.getCellNumber());
        memberDto.setPhoneNumber(myProfileBean.getPhoneNumber());
        memberDto.setForname(myProfileBean.getForname());
        memberDto.setSupplyDemandIds(new ArrayList<Long>());
        memberDto.setWealthSheet(wealthSheetDto);
        memberDto.setNotificationIds(new ArrayList<Long>());


        return memberDto;
    }

    public  static MembersDto myProfileBeanToDto(List<MyProfileBean> myProfilesBean){
        MembersDto membersDto = new MembersDto();
        for(MyProfileBean myProfileBean:myProfilesBean){
            membersDto.getMembers().add(myProfileBeanToDto(myProfileBean));
        }
        return membersDto;
    }

    private static MemberDto memberBeanToDto(MemberBean memberBean){
        MemberDto memberDto = new MemberDto();

        WealthSheetDto wealthSheetDto = new WealthSheetDto();
        wealthSheetDto.setId(ActivityConstant.BAD_ID);
        wealthSheetDto.setInitialAccount("0");
        wealthSheetDto.setFinalAccount("0");

        memberDto.setId(memberBean.getRemote_id());
        memberDto.setMobileId("");
        memberDto.setName(memberBean.getName());
        memberDto.setAddress(memberBean.getAddress());
        memberDto.setPostalCode(memberBean.getPostalCode());
        memberDto.setTown(memberBean.getTown());
        memberDto.setEmail(memberBean.getEmail());
        memberDto.setCellNumber(memberBean.getCellNumber());
        memberDto.setPhoneNumber(memberBean.getPhoneNumber());
        memberDto.setForname(memberBean.getForname());
        memberDto.setSupplyDemandIds(new ArrayList<Long>());
        memberDto.setWealthSheet(wealthSheetDto);
        memberDto.setNotificationIds(new ArrayList<Long>());

        return memberDto;
    }

    public static SupplyDemandDto supplyDemandBeanToDto(SupplyDemandBean supplyDemandBean){
        SupplyDemandDto supplyDemandDto = new SupplyDemandDto();

        supplyDemandDto.setId(supplyDemandBean.getRemote_id());
        supplyDemandDto.setType(supplyDemandBean.getType().getWording());
        supplyDemandDto.setCategory(supplyDemandBean.getCategory());
        supplyDemandDto.setTitle(supplyDemandBean.getTitle());
        supplyDemandDto.setMember(memberBeanToDto(supplyDemandBean.getMember()));

        return supplyDemandDto;
    }

    public static TransactionDto transactionBeanToDto(TransactionBean transactionBean){
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setCreditorMemberId(transactionBean.getCreditor().getRemote_id());
        transactionDto.setDebtorMemberId(transactionBean.getDebtor().getRemote_id());
        transactionDto.setSupplyDemandId(transactionBean.getSupplyDemand().getRemote_id());
        transactionDto.setAmount(transactionBean.getAmount().toString());

        return transactionDto;
    }

    public static WealthSheetDto wealthSheetBeanToDto(WealthSheetBean wealthSheetBean){
        WealthSheetDto wealthSheetDto = new WealthSheetDto();

        wealthSheetDto.setId(wealthSheetBean.getRemote_id());
        wealthSheetDto.setInitialAccount(wealthSheetBean.getInitialAccount().toString());
        wealthSheetDto.setFinalAccount(wealthSheetBean.getFinalAccount().toString());

        List<TransactionDto> transactionsDto = new ArrayList<TransactionDto>();
        for(TransactionBean transactionBean:wealthSheetBean.getTransactions()){
            transactionsDto.add(transactionBeanToDto(transactionBean));
        }
        wealthSheetDto.setTransactions(transactionsDto);

        return wealthSheetDto;
    }

    public static NotificationDto notificationBeanToDto(NotificationBean notificationBean){
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId(notificationBean.getRemote_id());
        notificationDto.setTitle(notificationBean.getTitle());
        notificationDto.setText(notificationBean.getText());
        notificationDto.setMembersToNotify(new ArrayList<MemberDto>());
        notificationDto.setTopic(new NotificationTopicDto());

        return notificationDto;
    }

    public static NotificationsDto notificationBeanToDto(List<NotificationBean> notificationsBean){
        NotificationsDto notificationsDto = new NotificationsDto();
        for(NotificationBean notificationBean:notificationsBean){
            notificationsDto.getNotifications().add(notificationBeanToDto(notificationBean));
        }
        return notificationsDto;
    }
}
