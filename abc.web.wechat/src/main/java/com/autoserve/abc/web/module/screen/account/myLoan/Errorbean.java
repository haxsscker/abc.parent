package com.autoserve.abc.web.module.screen.account.myLoan;

public class Errorbean
{
	/**
	 * 交易密码是否正确 1正确 0 错误
	 */
	private Integer isPsRight;
	/**
	 * 图片验证码是否正确 1正确 0 错误
	 */
	private Integer isImageCodeRight;
	public Integer getIsPsRight()
	{
		return isPsRight;
	}
	public void setIsPsRight(Integer isPsRight)
	{
		this.isPsRight = isPsRight;
	}
	public Integer getIsImageCodeRight()
	{
		return isImageCodeRight;
	}
	public void setIsImageCodeRight(Integer isImageCodeRight)
	{
		this.isImageCodeRight = isImageCodeRight;
	}
	
}
