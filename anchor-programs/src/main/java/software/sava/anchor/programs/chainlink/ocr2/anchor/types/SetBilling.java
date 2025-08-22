package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record SetBilling(int observationPaymentGjuels, int transmissionPaymentGjuels) implements Borsh {

  public static final int BYTES = 8;

  public static SetBilling read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var observationPaymentGjuels = getInt32LE(_data, i);
    i += 4;
    final var transmissionPaymentGjuels = getInt32LE(_data, i);
    return new SetBilling(observationPaymentGjuels, transmissionPaymentGjuels);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, observationPaymentGjuels);
    i += 4;
    putInt32LE(_data, i, transmissionPaymentGjuels);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
