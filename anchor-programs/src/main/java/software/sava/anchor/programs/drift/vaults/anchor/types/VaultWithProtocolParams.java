package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultWithProtocolParams(byte[] name,
                                      long redeemPeriod,
                                      long maxTokens,
                                      long managementFee,
                                      long minDepositAmount,
                                      int profitShare,
                                      int hurdleRate,
                                      int spotMarketIndex,
                                      boolean permissioned,
                                      VaultProtocolParams vaultProtocol) implements Borsh {

  public static final int BYTES = 119;
  public static final int NAME_LEN = 32;

  public static VaultWithProtocolParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var redeemPeriod = getInt64LE(_data, i);
    i += 8;
    final var maxTokens = getInt64LE(_data, i);
    i += 8;
    final var managementFee = getInt64LE(_data, i);
    i += 8;
    final var minDepositAmount = getInt64LE(_data, i);
    i += 8;
    final var profitShare = getInt32LE(_data, i);
    i += 4;
    final var hurdleRate = getInt32LE(_data, i);
    i += 4;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var permissioned = _data[i] == 1;
    ++i;
    final var vaultProtocol = VaultProtocolParams.read(_data, i);
    return new VaultWithProtocolParams(name,
                                       redeemPeriod,
                                       maxTokens,
                                       managementFee,
                                       minDepositAmount,
                                       profitShare,
                                       hurdleRate,
                                       spotMarketIndex,
                                       permissioned,
                                       vaultProtocol);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(name, _data, i);
    putInt64LE(_data, i, redeemPeriod);
    i += 8;
    putInt64LE(_data, i, maxTokens);
    i += 8;
    putInt64LE(_data, i, managementFee);
    i += 8;
    putInt64LE(_data, i, minDepositAmount);
    i += 8;
    putInt32LE(_data, i, profitShare);
    i += 4;
    putInt32LE(_data, i, hurdleRate);
    i += 4;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    _data[i] = (byte) (permissioned ? 1 : 0);
    ++i;
    i += Borsh.write(vaultProtocol, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
