package com.example.paymentservice.services;

import com.example.paymentservice.configs.PaypalConfigProperties;
import com.example.paymentservice.dtos.*;
import com.example.paymentservice.enums.AddressEnum;
import com.example.paymentservice.enums.PaymentStatusEnum;
import com.paypal.api.payments.*;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {

    private final APIContext apiContext;
    private final PaypalConfigProperties paypalConfigProperties;

    @Value("${paypal.redirect.cancel}")
    private String cancelUrl;
    @Value("${paypal.redirect.return}")
    private String returnUrl;

    @Override
    public String authorizePayment(OrderDetails orderDetails) throws PayPalRESTException {

        Payer payer = getPayer(orderDetails.getBuyer());
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(getTransactions(orderDetails));

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(transactions)
                .setRedirectUrls(redirectUrls)
                .setPayer(payer)
                .setIntent("authorize");

        log.info("Sending request to authorize payment");
        Payment payment = requestPayment.create(apiContext);
        return getApprovalLink(payment);
    }

    @Override
    public OrderDetailsFromPaypal getPaymentDetails(String paymentId) throws PayPalRESTException {
        log.info("Getting payment details with payment Id: " + paymentId);
        Payment payment = Payment.get(apiContext, paymentId);
        OrderDetailsFromPaypal orderDetailsFromPaypal = getOrderDetailsFromPaypal(payment);

        return orderDetailsFromPaypal;
    }

    private com.example.paymentservice.dtos.Payer getPayerObjectFromPayment(Payer payer) {
        com.example.paymentservice.dtos.PayerInfo payerInfo = getPayerInfo(payer.getPayerInfo());
        return com.example.paymentservice.dtos.Payer.builder()
                .paymentMethod(payer.getPaymentMethod())
                .payerInfo(payerInfo)
                .status(payer.getStatus())
                .build();
    }

    private com.example.paymentservice.dtos.PayerInfo getPayerInfo(PayerInfo payerInfo) {
        com.example.paymentservice.dtos.ShippingAddress shipmentAddress = getShipmentAddress(payerInfo.getShippingAddress());
        return com.example.paymentservice.dtos.PayerInfo.builder()
                .payerId(payerInfo.getPayerId())
                .firstName(payerInfo.getFirstName())
                .countryCode(payerInfo.getCountryCode())
                .email(payerInfo.getEmail())
                .lastName(payerInfo.getLastName())
                .shippingAddress(shipmentAddress)
                .build();
    }

    private com.example.paymentservice.dtos.ShippingAddress getShipmentAddress(ShippingAddress shippingAddress) {
        return com.example.paymentservice.dtos.ShippingAddress.builder()
                .postalCode(shippingAddress.getPostalCode())
                .city(shippingAddress.getCity())
                .line1(shippingAddress.getLine1())
                .recipientName(shippingAddress.getRecipientName())
                .countryCode(shippingAddress.getCountryCode())
                .state(shippingAddress.getState())
                .build();
    }

    private List<LinksItem> getLinks(List<Links> links) {
        List<LinksItem> linksItems = new ArrayList<>();
        links.forEach(linksItem -> linksItems.add(LinksItem.builder().href(linksItem.getHref()).method(linksItem.getMethod()).rel(linksItem.getRel()).build()));
        return linksItems;
    }

    private com.example.paymentservice.dtos.RedirectUrls getRedirectUrls(RedirectUrls redirectUrls) {
        return com.example.paymentservice.dtos.RedirectUrls.builder()
                .cancelUrl(redirectUrls.getCancelUrl())
                .returnUrl(redirectUrls.getReturnUrl())
                .build();
    }

    private List<TransactionsItem> getTransactionsItems(List<Transaction> transactions) {
        List<TransactionsItem> transactionsItems = new ArrayList<>();
        transactions.forEach(transaction -> {

            // set item list
            ItemList itemList = transaction.getItemList();
            List<ItemsItem> listOfItems = new ArrayList<>();
            itemList.getItems().forEach(item -> listOfItems.add(getItemsItem(item)));

            // set Transaction
            TransactionsItem transactionsItem = TransactionsItem.builder()
                    .itemList(getItemList(listOfItems))
                    .description(transaction.getDescription())
                    .amount(getAmount(transaction.getAmount()))
                    .build();
            transactionsItems.add(transactionsItem);

        });
        return transactionsItems;
    }

    private com.example.paymentservice.dtos.Amount getAmount(Amount amount) {
        return com.example.paymentservice.dtos.Amount.builder().currency(amount.getCurrency()).total(amount.getTotal()).build();
    }

    private com.example.paymentservice.dtos.ItemList getItemList(List<ItemsItem> listOfItems) {
        return com.example.paymentservice.dtos.ItemList.builder().items(listOfItems).build();
    }

    private ItemsItem getItemsItem(Item item) {
        return ItemsItem.builder()
                .currency(item.getCurrency())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

    @Override
    public PaymentStatus executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);
        Payment executedPayment = payment.execute(apiContext, paymentExecution);

        if ("approved".equals(executedPayment.getState())) {
            return PaymentStatus.builder().status(PaymentStatusEnum.APPROVED).build();
        }
        return PaymentStatus.builder().status(PaymentStatusEnum.OTHER).build();
    }

    private OrderDetailsFromPaypal getOrderDetailsFromPaypal(Payment executedPayment) {
        OrderDetailsFromPaypal orderDetailsFromPaypal = OrderDetailsFromPaypal.builder()
                .cart(executedPayment.getCart())
                .id(executedPayment.getId())
                .createTime(executedPayment.getCreateTime())
                .intent(executedPayment.getIntent())
                .state(executedPayment.getState())
                .updateTime(executedPayment.getUpdateTime())
                .build();

        // set transactions
        List<TransactionsItem> transactionsItems = getTransactionsItems(executedPayment.getTransactions());
        orderDetailsFromPaypal.setTransactions(transactionsItems);

        // set redirect urls
        com.example.paymentservice.dtos.RedirectUrls redirectUrls = getRedirectUrls(executedPayment.getRedirectUrls());
        orderDetailsFromPaypal.setRedirectUrls(redirectUrls);

        // set links
        List<LinksItem> links = getLinks(executedPayment.getLinks());
        orderDetailsFromPaypal.setLinks(links);

        // set Payer
        com.example.paymentservice.dtos.Payer payer = getPayerObjectFromPayment(executedPayment.getPayer());
        orderDetailsFromPaypal.setPayer(payer);
        return orderDetailsFromPaypal;
    }

    private String getApprovalLink(Payment payment) {
        List<Links> approvalUrl = payment.getLinks().stream().filter(link -> link.getRel().equalsIgnoreCase("approval_url")).collect(Collectors.toList());
        return approvalUrl.isEmpty() ? null : approvalUrl.get(0).getHref();
    }

    private Transaction getTransactions(OrderDetails orderDetails) {

        List<Product> products = orderDetails.getProducts();

        double totalShipmentFee = products.stream()
                .flatMapToDouble(product -> DoubleStream.of(product.getShippingFee()))
                .reduce(0, Double::sum);

        double totalPrice = products.stream()
                .flatMapToDouble(product -> DoubleStream.of(product.getPrice() * product.getQuantity()))
                .reduce(0, Double::sum);

        Details details = new Details();
        details.setShipping(String.valueOf(totalShipmentFee));
        details.setSubtotal(String.valueOf(totalPrice));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        double totalAmount = totalPrice + totalShipmentFee;
        amount.setTotal(String.valueOf(totalAmount));
        amount.setDetails(details);

        List<Item> items = new ArrayList<>();
        products.forEach(product -> items.add(new Item(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice()), "USD")));
        ItemList itemList = new ItemList();
        itemList.setItems(items);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetails.getOrderDescription());
        transaction.setItemList(itemList);

        return transaction;
    }

    private Payer getPayer(Buyer buyer) {
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(buyer.getFirstName());
        payerInfo.setLastName(buyer.getLastName());
        payerInfo.setEmail(buyer.getEmail());

        if (buyer.getAddresses().containsKey(AddressEnum.SHIPPING_ADDRESS)) {
            UserAddress buyerShippingAddress = buyer.getAddresses().get(AddressEnum.SHIPPING_ADDRESS);

            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setPostalCode(buyerShippingAddress.getPostalCode());
            shippingAddress.setCity(buyerShippingAddress.getCity());
            shippingAddress.setPostalCode(buyerShippingAddress.getPostalCode());
            shippingAddress.setState(buyerShippingAddress.getCountry());
            shippingAddress.setCountryCode(buyerShippingAddress.getCountryCode());
            payerInfo.setShippingAddress(shippingAddress);
        }

        if (buyer.getAddresses().containsKey(AddressEnum.BILLING_ADDRESS)) {
            UserAddress buyerBillingAddress = buyer.getAddresses().get(AddressEnum.BILLING_ADDRESS);

            Address address = new Address();
            address.setCity(buyerBillingAddress.getCity());
            address.setCountryCode(buyerBillingAddress.getCountryCode());
            address.setPostalCode(buyerBillingAddress.getPostalCode());
            address.setState(buyerBillingAddress.getCountry());
            payerInfo.setBillingAddress(address);
        }


            Payer payer = new Payer();
        payer.setPayerInfo(payerInfo);
        payer.setPaymentMethod("paypal");

        return payer;
    }
}
