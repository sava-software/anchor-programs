package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ManagerRepayRecord(long ts,
                                 PublicKey vault,
                                 PublicKey manager,
                                 long repayAmount,
                                 long repayValue,
                                 int repaySpotMarketIndex,
                                 long repayOraclePrice,
                                 int depositSpotMarketIndex,
                                 long depositOraclePrice,
                                 long vaultEquityBefore,
                                 long vaultEquityAfter) implements Borsh {

  public static final int BYTES = 124;

  public static ManagerRepayRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var manager = readPubKey(_data, i);
    i += 32;
    final var repayAmount = getInt64LE(_data, i);
    i += 8;
    final var repayValue = getInt64LE(_data, i);
    i += 8;
    final var repaySpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var repayOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var depositSpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var depositOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var vaultEquityBefore = getInt64LE(_data, i);
    i += 8;
    final var vaultEquityAfter = getInt64LE(_data, i);
    return new ManagerRepayRecord(ts,
                                  vault,
                                  manager,
                                  repayAmount,
                                  repayValue,
                                  repaySpotMarketIndex,
                                  repayOraclePrice,
                                  depositSpotMarketIndex,
                                  depositOraclePrice,
                                  vaultEquityBefore,
                                  vaultEquityAfter);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    vault.write(_data, i);
    i += 32;
    manager.write(_data, i);
    i += 32;
    putInt64LE(_data, i, repayAmount);
    i += 8;
    putInt64LE(_data, i, repayValue);
    i += 8;
    putInt16LE(_data, i, repaySpotMarketIndex);
    i += 2;
    putInt64LE(_data, i, repayOraclePrice);
    i += 8;
    putInt16LE(_data, i, depositSpotMarketIndex);
    i += 2;
    putInt64LE(_data, i, depositOraclePrice);
    i += 8;
    putInt64LE(_data, i, vaultEquityBefore);
    i += 8;
    putInt64LE(_data, i, vaultEquityAfter);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
