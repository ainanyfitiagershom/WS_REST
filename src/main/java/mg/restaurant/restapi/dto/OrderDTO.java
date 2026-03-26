package mg.restaurant.restapi.dto;

import java.util.List;

public class OrderDTO {

    private Long userId;
    private Long restaurantId;
    private List<Long> menuIds;

    public OrderDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public List<Long> getMenuIds() { return menuIds; }
    public void setMenuIds(List<Long> menuIds) { this.menuIds = menuIds; }
}
