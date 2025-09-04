package software.sava.anchor.programs.chainlink.store;

import software.sava.anchor.programs.chainlink.store.anchor.StoreConstants;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import java.math.BigInteger;

import static software.sava.core.encoding.ByteUtil.*;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Transmission(long slot,
                           int timestamp,
                           int padding0,
                           BigInteger answer,
                           int padding1,
                           int padding2) implements Borsh {

  public static final int BYTES = 40;

  public static final Discriminator TRANSMISSIONS_DISCRIMINATOR = toDiscriminator(96, 179, 69, 66, 128, 129, 73, 117);

  public static Filter discriminatorFilter() {
    return Filter.createMemCompFilter(
        0,
        Transmission.TRANSMISSIONS_DISCRIMINATOR.data()
    );
  }

  public static Transmission parseTransmission(final byte[] transmissionsData, final int cursor) {
    if (transmissionsData == null || transmissionsData.length == 0) {
      return null;
    } else {
      final int offset = Discriminator.ANCHOR_DISCRIMINATOR_LENGTH
          + Math.toIntExact(StoreConstants.HEADER_SIZE)
          + (cursor * BYTES);
      return read(transmissionsData, offset);
    }
  }

  public static Transmission[] parseTransmissions(final byte[] transmissionsData) {
    if (transmissionsData == null || transmissionsData.length == 0) {
      return null;
    }
    int offset = Discriminator.ANCHOR_DISCRIMINATOR_LENGTH + Math.toIntExact(StoreConstants.HEADER_SIZE);
    final int numTransmissions = (transmissionsData.length - offset) / BYTES;
    final var transmissions = new Transmission[numTransmissions];
    for (int i = 0; i < numTransmissions; i++) {
      transmissions[i] = read(transmissionsData, offset);
      offset += BYTES;
    }
    return transmissions;
  }

  public static Transmission read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var timestamp = getInt32LE(_data, i);
    i += 4;
    final var padding0 = getInt32LE(_data, i);
    i += 4;
    final var answer = getInt128LE(_data, i);
    i += 16;
    final var padding1 = getInt32LE(_data, i);
    i += 4;
    final var padding2 = getInt32LE(_data, i);
    return new Transmission(slot,
        timestamp,
        padding0,
        answer,
        padding1,
        padding2
    );
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt32LE(_data, i, timestamp);
    i += 4;
    putInt32LE(_data, i, padding0);
    i += 4;
    putInt128LE(_data, i, answer);
    i += 16;
    putInt32LE(_data, i, padding1);
    i += 4;
    putInt32LE(_data, i, padding2);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
