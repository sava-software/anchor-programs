package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record BaseFeeParameter(// Portion of swap fees retained by the protocol by controlling protocol_share parameter. protocol_swap_fee = protocol_share * total_swap_fee
                               int protocolShare,
                               // Base factor for base fee rate
                               int baseFactor,
                               // Base fee power factor
                               int baseFeePowerFactor) implements Borsh {

  public static final int BYTES = 5;

  public static BaseFeeParameter read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var protocolShare = getInt16LE(_data, i);
    i += 2;
    final var baseFactor = getInt16LE(_data, i);
    i += 2;
    final var baseFeePowerFactor = _data[i] & 0xFF;
    return new BaseFeeParameter(protocolShare, baseFactor, baseFeePowerFactor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, protocolShare);
    i += 2;
    putInt16LE(_data, i, baseFactor);
    i += 2;
    _data[i] = (byte) baseFeePowerFactor;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
