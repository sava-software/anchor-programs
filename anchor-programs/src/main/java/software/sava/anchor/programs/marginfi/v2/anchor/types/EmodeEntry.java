package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record EmodeEntry(// emode_tag of the bank(s) whose collateral you wish to treat preferentially.
                         int collateralBankEmodeTag,
                         // * APPLIES_TO_ISOLATED (1) - (NOT YET IMPLEMENTED) if set, isolated banks with this tag
                         // also benefit. If not set, isolated banks continue to offer zero collateral, even if they
                         // use this tag.
                         // * 2, 4, 8, 16, 32, etc - reserved for future use
                         int flags,
                         byte[] pad0,
                         // Note: If set below the collateral bank's weight, does nothing.
                         WrappedI80F48 assetWeightInit,
                         // Note: If set below the collateral bank's weight, does nothing.
                         WrappedI80F48 assetWeightMaint) implements Borsh {

  public static final int BYTES = 40;
  public static final int PAD_0_LEN = 5;

  public static EmodeEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralBankEmodeTag = getInt16LE(_data, i);
    i += 2;
    final var flags = _data[i] & 0xFF;
    ++i;
    final var pad0 = new byte[5];
    i += Borsh.readArray(pad0, _data, i);
    final var assetWeightInit = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightInit);
    final var assetWeightMaint = WrappedI80F48.read(_data, i);
    return new EmodeEntry(collateralBankEmodeTag,
                          flags,
                          pad0,
                          assetWeightInit,
                          assetWeightMaint);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, collateralBankEmodeTag);
    i += 2;
    _data[i] = (byte) flags;
    ++i;
    i += Borsh.writeArray(pad0, _data, i);
    i += Borsh.write(assetWeightInit, _data, i);
    i += Borsh.write(assetWeightMaint, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
