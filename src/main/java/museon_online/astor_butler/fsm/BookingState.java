package museon_online.astor_butler.fsm;

public enum BookingState {
    SELECTING_LOCATION,
    SELECTING_DATE,
    SELECTING_SLOT,
    SELECTING_TABLE,
    CONFIRMING,
    COMPLETED
}
