package software.sava.anchor.programs.drift.anchor.types;

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

public record LiquidationRecord(long ts,
                                LiquidationType liquidationType,
                                PublicKey user,
                                PublicKey liquidator,
                                BigInteger marginRequirement,
                                BigInteger totalCollateral,
                                long marginFreed,
                                int liquidationId,
                                boolean bankrupt,
                                int[] canceledOrderIds,
                                LiquidatePerpRecord liquidatePerp,
                                LiquidateSpotRecord liquidateSpot,
                                LiquidateBorrowForPerpPnlRecord liquidateBorrowForPerpPnl,
                                LiquidatePerpPnlForDepositRecord liquidatePerpPnlForDeposit,
                                PerpBankruptcyRecord perpBankruptcy,
                                SpotBankruptcyRecord spotBankruptcy) implements Borsh {

  public static LiquidationRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var liquidationType = LiquidationType.read(_data, i);
    i += Borsh.len(liquidationType);
    final var user = readPubKey(_data, i);
    i += 32;
    final var liquidator = readPubKey(_data, i);
    i += 32;
    final var marginRequirement = getInt128LE(_data, i);
    i += 16;
    final var totalCollateral = getInt128LE(_data, i);
    i += 16;
    final var marginFreed = getInt64LE(_data, i);
    i += 8;
    final var liquidationId = getInt16LE(_data, i);
    i += 2;
    final var bankrupt = _data[i] == 1;
    ++i;
    final var canceledOrderIds = Borsh.readintVector(_data, i);
    i += Borsh.len(canceledOrderIds);
    final var liquidatePerp = LiquidatePerpRecord.read(_data, i);
    i += Borsh.len(liquidatePerp);
    final var liquidateSpot = LiquidateSpotRecord.read(_data, i);
    i += Borsh.len(liquidateSpot);
    final var liquidateBorrowForPerpPnl = LiquidateBorrowForPerpPnlRecord.read(_data, i);
    i += Borsh.len(liquidateBorrowForPerpPnl);
    final var liquidatePerpPnlForDeposit = LiquidatePerpPnlForDepositRecord.read(_data, i);
    i += Borsh.len(liquidatePerpPnlForDeposit);
    final var perpBankruptcy = PerpBankruptcyRecord.read(_data, i);
    i += Borsh.len(perpBankruptcy);
    final var spotBankruptcy = SpotBankruptcyRecord.read(_data, i);
    return new LiquidationRecord(ts,
                                 liquidationType,
                                 user,
                                 liquidator,
                                 marginRequirement,
                                 totalCollateral,
                                 marginFreed,
                                 liquidationId,
                                 bankrupt,
                                 canceledOrderIds,
                                 liquidatePerp,
                                 liquidateSpot,
                                 liquidateBorrowForPerpPnl,
                                 liquidatePerpPnlForDeposit,
                                 perpBankruptcy,
                                 spotBankruptcy);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    i += Borsh.write(liquidationType, _data, i);
    user.write(_data, i);
    i += 32;
    liquidator.write(_data, i);
    i += 32;
    putInt128LE(_data, i, marginRequirement);
    i += 16;
    putInt128LE(_data, i, totalCollateral);
    i += 16;
    putInt64LE(_data, i, marginFreed);
    i += 8;
    putInt16LE(_data, i, liquidationId);
    i += 2;
    _data[i] = (byte) (bankrupt ? 1 : 0);
    ++i;
    i += Borsh.write(canceledOrderIds, _data, i);
    i += Borsh.write(liquidatePerp, _data, i);
    i += Borsh.write(liquidateSpot, _data, i);
    i += Borsh.write(liquidateBorrowForPerpPnl, _data, i);
    i += Borsh.write(liquidatePerpPnlForDeposit, _data, i);
    i += Borsh.write(perpBankruptcy, _data, i);
    i += Borsh.write(spotBankruptcy, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + Borsh.len(liquidationType)
         + 32
         + 32
         + 16
         + 16
         + 8
         + 2
         + 1
         + Borsh.len(canceledOrderIds)
         + Borsh.len(liquidatePerp)
         + Borsh.len(liquidateSpot)
         + Borsh.len(liquidateBorrowForPerpPnl)
         + Borsh.len(liquidatePerpPnlForDeposit)
         + Borsh.len(perpBankruptcy)
         + Borsh.len(spotBankruptcy);
  }
}
