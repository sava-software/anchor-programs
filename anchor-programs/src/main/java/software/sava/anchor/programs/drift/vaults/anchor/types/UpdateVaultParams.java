package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.lang.Boolean;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdateVaultParams(OptionalLong redeemPeriod,
                                OptionalLong maxTokens,
                                OptionalLong managementFee,
                                OptionalLong minDepositAmount,
                                OptionalInt profitShare,
                                OptionalInt hurdleRate,
                                Boolean permissioned) implements Borsh {

  public static UpdateVaultParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var redeemPeriod = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (redeemPeriod.isPresent()) {
      i += 8;
    }
    final var maxTokens = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxTokens.isPresent()) {
      i += 8;
    }
    final var managementFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (managementFee.isPresent()) {
      i += 8;
    }
    final var minDepositAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minDepositAmount.isPresent()) {
      i += 8;
    }
    final var profitShare = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (profitShare.isPresent()) {
      i += 4;
    }
    final var hurdleRate = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (hurdleRate.isPresent()) {
      i += 4;
    }
    final var permissioned = _data[i++] == 0 ? null : _data[i] == 1;
    return new UpdateVaultParams(redeemPeriod,
                                 maxTokens,
                                 managementFee,
                                 minDepositAmount,
                                 profitShare,
                                 hurdleRate,
                                 permissioned);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(redeemPeriod, _data, i);
    i += Borsh.writeOptional(maxTokens, _data, i);
    i += Borsh.writeOptional(managementFee, _data, i);
    i += Borsh.writeOptional(minDepositAmount, _data, i);
    i += Borsh.writeOptional(profitShare, _data, i);
    i += Borsh.writeOptional(hurdleRate, _data, i);
    i += Borsh.writeOptional(permissioned, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (redeemPeriod == null || redeemPeriod.isEmpty() ? 1 : (1 + 8))
         + (maxTokens == null || maxTokens.isEmpty() ? 1 : (1 + 8))
         + (managementFee == null || managementFee.isEmpty() ? 1 : (1 + 8))
         + (minDepositAmount == null || minDepositAmount.isEmpty() ? 1 : (1 + 8))
         + (profitShare == null || profitShare.isEmpty() ? 1 : (1 + 4))
         + (hurdleRate == null || hurdleRate.isEmpty() ? 1 : (1 + 4))
         + (permissioned == null ? 1 : (1 + 1));
  }
}
