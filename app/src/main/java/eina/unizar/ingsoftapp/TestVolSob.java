package eina.unizar.ingsoftapp;


import android.content.Context;

public class TestVolSob extends Test {
    private static final String ROOM_TAG    = "Habitacion";
    private RoomsDbAdapter roomsDBHelper;
    private ReservationDbAdapter reservationsDBHelper;

    private static String room = "hab_";
    private static String res = "res_";
    private static int MAX_ROOMS = 100;
    private static int MAX_RES = 2000;

    private final Context mCtx;

    // private final Context mCtx;

    public TestVolSob(Context ctx) {
        this.mCtx = ctx;
        roomsDBHelper = new RoomsDbAdapter(ctx);
        roomsDBHelper.open();
        reservationsDBHelper = new ReservationDbAdapter(ctx);
        reservationsDBHelper.open();
    }

    public void crearCienHabitaciones() {
        for (int i = 0; i < MAX_ROOMS; i++) {
            roomsDBHelper.createHabitacion(room+ Integer.toString(i), "habitacion", "1", "1","1");
        }
    }

    public void borrarHabitaciones() {
        roomsDBHelper.deleteAllHabitaciones();
    }

    public void crearDosmilReservas() {
        for (int i = 0; i < MAX_RES; i++) {
            reservationsDBHelper.createReserva(res+ Integer.toString(i), "999999999", "15/01/2023", "16/01/2023","1");
        }
    }

    public void borrarReservas() {
        reservationsDBHelper.deleteAllReservas();
    }

}