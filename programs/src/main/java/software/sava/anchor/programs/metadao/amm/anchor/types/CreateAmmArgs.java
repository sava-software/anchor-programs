package software.sava.anchor.programs.metadao.amm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record CreateAmmArgs(BigInteger twapInitialObservation, BigInteger twapMaxObservationChangePerUpdate) implements Borsh {

  public static final int BYTES = 32;

  public static CreateAmmArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    return new CreateAmmArgs(twapInitialObservation, twapMaxObservationChangePerUpdate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
