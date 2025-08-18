package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TransferFeeExtensionParams(// denominator is 10000, currently, this value cannot exceed 5%, which is 500.
                                         int transferFeeBasisPoints,
                                         // Maximum fee on each transfers, the value must exceed supply * transfer_fee_basis_points / 10000
                                         long maximumFee) implements Borsh {

  public static final int BYTES = 10;

  public static TransferFeeExtensionParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var transferFeeBasisPoints = getInt16LE(_data, i);
    i += 2;
    final var maximumFee = getInt64LE(_data, i);
    return new TransferFeeExtensionParams(transferFeeBasisPoints, maximumFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, transferFeeBasisPoints);
    i += 2;
    putInt64LE(_data, i, maximumFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
