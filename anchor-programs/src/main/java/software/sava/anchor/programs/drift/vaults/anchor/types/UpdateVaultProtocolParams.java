package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdateVaultProtocolParams(OptionalLong protocolFee, OptionalInt protocolProfitShare) implements Borsh {

  public static UpdateVaultProtocolParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var protocolFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (protocolFee.isPresent()) {
      i += 8;
    }
    final var protocolProfitShare = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    return new UpdateVaultProtocolParams(protocolFee, protocolProfitShare);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(protocolFee, _data, i);
    i += Borsh.writeOptional(protocolProfitShare, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (protocolFee == null || protocolFee.isEmpty() ? 1 : (1 + 8)) + (protocolProfitShare == null || protocolProfitShare.isEmpty() ? 1 : (1 + 4));
  }
}
