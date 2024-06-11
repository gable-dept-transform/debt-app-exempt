package th.co.ais.mimo.debt.exempt.model;

public interface DMSEM004GetBaByMobileNumDto {
	String getBillAccNum();

	String getMobileNum();

	static DMSEM004GetBaByMobileNumDto createWithMobileNum(String mobileNum) {
		return new DMSEM004GetBaByMobileNumDto() {
			@Override
			public String getBillAccNum() {
				return null;
			}

			@Override
			public String getMobileNum() {
				return mobileNum;
			}
		};
	}
}
