package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ReservationV1(PublicKey address,
                            int spotsRemaining,
                            int totalSpots) implements Borsh {

  public static final int BYTES = 34;

  public static ReservationV1 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var address = readPubKey(_data, i);
    i += 32;
    final var spotsRemaining = _data[i] & 0xFF;
    ++i;
    final var totalSpots = _data[i] & 0xFF;
    return new ReservationV1(address, spotsRemaining, totalSpots);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    address.write(_data, i);
    i += 32;
    _data[i] = (byte) spotsRemaining;
    ++i;
    _data[i] = (byte) totalSpots;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
