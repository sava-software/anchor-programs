package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultDepositorV1Record(long ts,
                                     PublicKey vault,
                                     PublicKey depositorAuthority,
                                     VaultDepositorAction action,
                                     long amount,
                                     int spotMarketIndex,
                                     BigInteger vaultSharesBefore,
                                     BigInteger vaultSharesAfter,
                                     long vaultEquityBefore,
                                     BigInteger userVaultSharesBefore,
                                     BigInteger totalVaultSharesBefore,
                                     BigInteger userVaultSharesAfter,
                                     BigInteger totalVaultSharesAfter,
                                     BigInteger protocolSharesBefore,
                                     BigInteger protocolSharesAfter,
                                     long protocolProfitShare,
                                     long protocolFee,
                                     long protocolFeeShares,
                                     long managerProfitShare,
                                     long managementFee,
                                     long managementFeeShares,
                                     long depositOraclePrice) implements Borsh {

  public static final int BYTES = 275;

  public static VaultDepositorV1Record read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var depositorAuthority = readPubKey(_data, i);
    i += 32;
    final var action = VaultDepositorAction.read(_data, i);
    i += Borsh.len(action);
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var vaultSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var vaultSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var vaultEquityBefore = getInt64LE(_data, i);
    i += 8;
    final var userVaultSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var totalVaultSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var userVaultSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var totalVaultSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var protocolSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var protocolSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var protocolProfitShare = getInt64LE(_data, i);
    i += 8;
    final var protocolFee = getInt64LE(_data, i);
    i += 8;
    final var protocolFeeShares = getInt64LE(_data, i);
    i += 8;
    final var managerProfitShare = getInt64LE(_data, i);
    i += 8;
    final var managementFee = getInt64LE(_data, i);
    i += 8;
    final var managementFeeShares = getInt64LE(_data, i);
    i += 8;
    final var depositOraclePrice = getInt64LE(_data, i);
    return new VaultDepositorV1Record(ts,
                                      vault,
                                      depositorAuthority,
                                      action,
                                      amount,
                                      spotMarketIndex,
                                      vaultSharesBefore,
                                      vaultSharesAfter,
                                      vaultEquityBefore,
                                      userVaultSharesBefore,
                                      totalVaultSharesBefore,
                                      userVaultSharesAfter,
                                      totalVaultSharesAfter,
                                      protocolSharesBefore,
                                      protocolSharesAfter,
                                      protocolProfitShare,
                                      protocolFee,
                                      protocolFeeShares,
                                      managerProfitShare,
                                      managementFee,
                                      managementFeeShares,
                                      depositOraclePrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    vault.write(_data, i);
    i += 32;
    depositorAuthority.write(_data, i);
    i += 32;
    i += Borsh.write(action, _data, i);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt128LE(_data, i, vaultSharesBefore);
    i += 16;
    putInt128LE(_data, i, vaultSharesAfter);
    i += 16;
    putInt64LE(_data, i, vaultEquityBefore);
    i += 8;
    putInt128LE(_data, i, userVaultSharesBefore);
    i += 16;
    putInt128LE(_data, i, totalVaultSharesBefore);
    i += 16;
    putInt128LE(_data, i, userVaultSharesAfter);
    i += 16;
    putInt128LE(_data, i, totalVaultSharesAfter);
    i += 16;
    putInt128LE(_data, i, protocolSharesBefore);
    i += 16;
    putInt128LE(_data, i, protocolSharesAfter);
    i += 16;
    putInt64LE(_data, i, protocolProfitShare);
    i += 8;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    putInt64LE(_data, i, protocolFeeShares);
    i += 8;
    putInt64LE(_data, i, managerProfitShare);
    i += 8;
    putInt64LE(_data, i, managementFee);
    i += 8;
    putInt64LE(_data, i, managementFeeShares);
    i += 8;
    putInt64LE(_data, i, depositOraclePrice);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
