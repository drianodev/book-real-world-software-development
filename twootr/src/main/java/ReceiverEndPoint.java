
/**
 * Adapter interface for pushing information out to a UI port.
 */
// tag::ReceiverEndPoint[]
public interface ReceiverEndPoint {
    void onTwoot(Twoot twoot);
}
// end::ReceiverEndPoint[]
