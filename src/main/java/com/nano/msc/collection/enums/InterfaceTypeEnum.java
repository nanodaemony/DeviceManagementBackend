package com.nano.msc.collection.enums;

import lombok.Getter;

/**
 * 仪器接口类型枚举
 * @author cz
 */
@Getter
public enum InterfaceTypeEnum {

	/**
	 * 以太网
	 */
	ETHERNET(1, "以太网"),

	/**
	 * 串口类
	 */
	SERIAL(2, "串口类"),
	;

	/**
	 * 仪器类型code
	 */
	int code;

	/**
	 * 类型名称
	 */
	String typeName;

	InterfaceTypeEnum(int code, String typeName) {
		this.code = code;
		this.typeName = typeName;
	}

	/**
	 * 判断是否是一个合法的类型号
	 */
	public static boolean isValidType(int typeCode) {
		for (InterfaceTypeEnum typeEnum : InterfaceTypeEnum.values()) {
			if (typeEnum.getCode() == typeCode) {
				return true;
			}
		}
		return false;
	}


}

