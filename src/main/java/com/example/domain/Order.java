package com.example.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
@Table(name="TB_ORDERS")
public class Order extends AbstractRegInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; //주문회원

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;      //배송정보

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList=new ArrayList<>();

    @Column(name="order_date")
    private LocalDateTime orderDate;//주문시간

    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status; //주문상태 (ORDER, CANCEL)

    //==연관관계 메서드==//
    //ManyToOne일 경우
    public void injectMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }
    //OneToOne일경우
    public void injectDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //OneToMany일 경우
    public void injectOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public Order() {}
}
