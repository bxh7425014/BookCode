package com.lyq.util.hibernate;

import com.lyq.model.OrderState;
/**
 * 订单状态Hibernate映射类型
 * @author Li Yongqiang
 */
public class OrderStateType extends EnumType<OrderState> {
	public OrderStateType() {
		super(OrderState.class);
	}
}
