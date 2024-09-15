package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record FeeParameter(// Portion of swap fees retained by the protocol by controlling protocol_share parameter. protocol_swap_fee = protocol_share * total_swap_fee
                           int protocolShare,
                           // Base factor for base fee rate
                           int baseFactor) implements Borsh {

  public static final int BYTES = 4;

  public static FeeParameter read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var protocolShare = getInt16LE(_data, i);
    i += 2;
    final var baseFactor = getInt16LE(_data, i);
    return new FeeParameter(protocolShare, baseFactor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, protocolShare);
    i += 2;
    putInt16LE(_data, i, baseFactor);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
