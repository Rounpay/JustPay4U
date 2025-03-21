package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.SerializedName;

public class RankList{

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("installmentInterVal")
	private int installmentInterVal;

	@SerializedName("giftImage")
	private String giftImage;

	@SerializedName("directCount")
	private int directCount;

	@SerializedName("benefitType")
	private Object benefitType;

	@SerializedName("rankImage")
	private String rankImage;

	@SerializedName("incentiveAmount")
	private Object incentiveAmount;

	@SerializedName("isRankWiseComm")
	private boolean isRankWiseComm;

	@SerializedName("userRank")
	private int userRank;

	@SerializedName("userID")
	private int userID;

	@SerializedName("achieveInDays")
	private int achieveInDays;

	@SerializedName("displayFields")
	private String displayFields;

	@SerializedName("rankWiseComm")
	private Object rankWiseComm;

	@SerializedName("rankid")
	private int rankid;

	@SerializedName("dailyWithDrawLimit")
	private Object dailyWithDrawLimit;

	@SerializedName("directBusiness")
	private Object directBusiness;

	@SerializedName("id")
	private int id;

	@SerializedName("reward")
	private Object reward;

	@SerializedName("juniorCount")
	private Object juniorCount;

	@SerializedName("rankOneTimeReward")
	private int rankOneTimeReward;

	@SerializedName("matchingBusiness")
	private Object matchingBusiness;

	@SerializedName("incentiveCurrencyID")
	private int incentiveCurrencyID;

	@SerializedName("monthlyWithdrawLimit")
	private Object monthlyWithdrawLimit;

	@SerializedName("juniorRankId")
	private int juniorRankId;

	@SerializedName("sponserBusiness")
	private Object sponserBusiness;

	@SerializedName("incentiveSymbol")
	private String incentiveSymbol;

	@SerializedName("selfBusiness")
	private Object selfBusiness;

	@SerializedName("rewardCurrencyID")
	private int rewardCurrencyID;

	@SerializedName("isInstallment")
	private boolean isInstallment;

	@SerializedName("isRankWiseWithDrawal")
	private boolean isRankWiseWithDrawal;

	@SerializedName("installment")
	private Object installment;

	@SerializedName("rankname")
	private String rankname;

	@SerializedName("bussinessCurrencyId")
	private int bussinessCurrencyId;

	@SerializedName("rewardSymbol")
	private String rewardSymbol;
	@SerializedName("remainingDays")
	private String remainingDays;
	@SerializedName("holdCommAmt")
	private int holdCommAmt;

	public String getSymbol(){
		return symbol;
	}

	public int getInstallmentInterVal(){
		return installmentInterVal;
	}

	public String getGiftImage(){
		return giftImage;
	}

	public int getDirectCount(){
		return directCount;
	}

	public Object getBenefitType(){
		return benefitType;
	}

	public String getRankImage(){
		return rankImage;
	}

	public Object getIncentiveAmount(){
		return incentiveAmount;
	}

	public boolean isIsRankWiseComm(){
		return isRankWiseComm;
	}

	public int getUserRank(){
		return userRank;
	}

	public int getUserID(){
		return userID;
	}

	public int getAchieveInDays(){
		return achieveInDays;
	}

	public String getDisplayFields(){
		return displayFields;
	}

	public Object getRankWiseComm(){
		return rankWiseComm;
	}

	public int getRankid(){
		return rankid;
	}

	public Object getDailyWithDrawLimit(){
		return dailyWithDrawLimit;
	}

	public Object getDirectBusiness(){
		return directBusiness;
	}

	public int getId(){
		return id;
	}

	public Object getReward(){
		return reward;
	}

	public Object getJuniorCount(){
		return juniorCount;
	}

	public int getRankOneTimeReward(){
		return rankOneTimeReward;
	}

	public Object getMatchingBusiness(){
		return matchingBusiness;
	}

	public int getIncentiveCurrencyID(){
		return incentiveCurrencyID;
	}

	public Object getMonthlyWithdrawLimit(){
		return monthlyWithdrawLimit;
	}

	public int getJuniorRankId(){
		return juniorRankId;
	}

	public Object getSponserBusiness(){
		return sponserBusiness;
	}

	public String getIncentiveSymbol(){
		return incentiveSymbol;
	}

	public Object getSelfBusiness(){
		return selfBusiness;
	}

	public int getRewardCurrencyID(){
		return rewardCurrencyID;
	}

	public boolean isIsInstallment(){
		return isInstallment;
	}

	public boolean isIsRankWiseWithDrawal(){
		return isRankWiseWithDrawal;
	}

	public Object getInstallment(){
		return installment;
	}

	public String getRankname(){
		return rankname;
	}

	public int getBussinessCurrencyId(){
		return bussinessCurrencyId;
	}

	public String getRewardSymbol(){
		return rewardSymbol;
	}
	public String getRemainingDays(){
		return remainingDays;
	}

	public int getHoldCommAmt() {
		return holdCommAmt;
	}

}