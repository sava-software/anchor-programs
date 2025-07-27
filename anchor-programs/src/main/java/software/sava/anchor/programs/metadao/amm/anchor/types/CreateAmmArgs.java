package software.sava.anchor.programs.metadao.amm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CreateAmmArgs(BigInteger twapInitialObservation,
                            BigInteger twapMaxObservationChangePerUpdate,
                            long twapStartDelaySlots) implements Borsh {

  public static final int BYTES = 40;

  public static CreateAmmArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var twapStartDelaySlots = getInt64LE(_data, i);
    return new CreateAmmArgs(twapInitialObservation, twapMaxObservationChangePerUpdate, twapStartDelaySlots);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    putInt64LE(_data, i, twapStartDelaySlots);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
