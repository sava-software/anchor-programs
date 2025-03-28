package software.sava.anchor.programs.meteora.dlmm;

import org.junit.jupiter.api.Test;
import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.TransactionSkeleton;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.math.MathContext;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram.CLAIM_FEE2_DISCRIMINATOR;
import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram.REMOVE_LIQUIDITY_BY_RANGE2_DISCRIMINATOR;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

final class MeteoraDlmmTests {

  @Test
  void testBinPricing() {
    final int baseScale = 8;
    final int quoteScale = 6;
    final int scaleDifference = DlmmUtils.scaleDifference(baseScale, quoteScale);
    assertEquals(-2, scaleDifference);

    final int stepSize = 4;
    final var binStepBase = DlmmUtils.binStepBase(stepSize);

    final int binId = 17221;

    final var binPrice = DlmmUtils.binPrice(binStepBase, binId, scaleDifference, MathContext.DECIMAL64);
    assertEquals("97948.08508510689", binPrice.stripTrailingZeros().toPlainString());

    double calculatedBinId = DlmmUtils.binId(binPrice, scaleDifference, binStepBase);
    assertEquals(binId, Math.round(calculatedBinId));

    final double binStepBaseDouble = binStepBase.doubleValue();
    assertEquals(binStepBaseDouble, DlmmUtils.binStepBaseDouble(stepSize));

    double doubleBinPrice = DlmmUtils.binPrice(binStepBaseDouble, binId, scaleDifference);
    assertEquals(97948.08508503261, doubleBinPrice, 0.00000000001);

    final double inversePriceFactor = DlmmUtils.priceScaleFactor(-scaleDifference);
    assertEquals(100, inversePriceFactor);
    doubleBinPrice = DlmmUtils.binPrice(binStepBaseDouble, binId, inversePriceFactor);
    assertEquals(97948.08508503261, doubleBinPrice, 0.00000000001);

    calculatedBinId = DlmmUtils.binId(doubleBinPrice, scaleDifference, binStepBaseDouble);
    assertEquals(binId, Math.round(calculatedBinId));

    final double logBinStepBase = StrictMath.log(binStepBaseDouble);
    calculatedBinId = DlmmUtils.binIdFromLogBinStepBase(doubleBinPrice, scaleDifference, logBinStepBase);
    assertEquals(binId, Math.round(calculatedBinId));

    final double inverseLogBinStepBase = 1 / logBinStepBase;
    calculatedBinId = DlmmUtils.binIdFromInverseLogBinStepBase(doubleBinPrice, scaleDifference, inverseLogBinStepBase);
    assertEquals(binId, Math.round(calculatedBinId));

    final double priceScaleFactor = DlmmUtils.priceScaleFactor(scaleDifference);
    calculatedBinId = DlmmUtils.binIdFromInverseLogBinStepBase(doubleBinPrice, priceScaleFactor, inverseLogBinStepBase);
    assertEquals(binId, Math.round(calculatedBinId));
  }

  @Test
  void testWithdrawAndClosePosition() {
    final var solAccounts = SolanaAccounts.MAIN_NET;
    final var metAccounts = MeteoraAccounts.MAIN_NET;

    final var feePayer = PublicKey.fromBase58Encoded("savaKKJmmwDsHHhxV6G293hrRM4f1p6jv6qUF441QD3");
    final var nativeAccountClient = NativeProgramAccountClient.createClient(feePayer);
    final var dlmmClient = MeteoraDlmmClient.createClient(nativeAccountClient);

    var position = fromBase58Encoded("6RukQNq3q4nRwEvMxHF4sAKYeYLrMS9u5C68ebCkRhSo");
    var lbPair = fromBase58Encoded("2dBPJGLgNDZnzA32452zV2u6vensbo28dveBvecDg6X1");
    var userTokenX = fromBase58Encoded("ARda5vKyfs2YXApqbf8jQpFX31jopRhx8FheZ1pU1qzT");
    var userTokenY = fromBase58Encoded("BJyBao69dq82iLhM7KrSUP3LsfVu49UHkkrMC41YYQpy");
    var reserveX = fromBase58Encoded("76mR3RnAziZGa6CzrfdJNkN6rh9KLea2FszvgF2LMJvS");
    var reserveY = fromBase58Encoded("AcSwnB4eQ1mH95V4LvaPLiiHAFSk6rsLX41Y9jCSecVF");
    var xMint = fromBase58Encoded("mSoLzYCxHdYgdzU16g5QSh3i5K3z3KZK7ytfqcJm7So");
    var yMint = fromBase58Encoded("So11111111111111111111111111111111111111112");
    var binArrayLower = fromBase58Encoded("6Na8HRhAR4obBzAey4n11vr8NJD56hFuVAtrTNaEhDYg");

    final int lowerBinId = 2425;
    final int upperBinId = 2436;
    var binArrayAccountMetas = dlmmClient.deriveBinAccounts(lbPair, lowerBinId, upperBinId);

    final byte[] data = Base64.getDecoder().decode("""
        AQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAgRDPVl6eB0qtYSlYif4b0tHW4ZfMrzSctd89y3PLhgsgZP0RY6Q0W/frzwgb8tnEGSapiDquhyHhfkmp1muCulzVCsBEtVZftYLWi/JVJP7pM8wht+/nNVfX0Gu0HtOpTIWqA+yQzqjmLux3w7/C2B02Tqi6U0esHGgo87U30CvG+MCHjZjfXHR2P1EBor4P6rxSVuBFovATqVpsXS7g1B1JkvRMtUjSS3E0YWcPOiIGR22BXeLO6LVQhD5AQ4utEiBOnhL7yE6CbJMszp4mQMzhVZDBxic7CSVwi6O4UgsLyOzdiddVSoOFDTXcEcLijCzeBdioHPFW72ST8uULYUChggVni5HWPKTOcOlR4oWEv+EfPZZCCPw8HbhCZjHbn4C2K6B09yLJ1BFPLY9woAxmACM3ub+QyHNlem0gHbTIAGm4hX/quBhPtof2NGGMA12sQ53BrrO1WYoPAAAAAAAQbd9uHXZaGT2cvhRs7reawctIXtX1s3kTqM9YV+/wCpsnDWf6mMUc8CEwUTWJYrrzV0K+1ZydlEXpwNDIXHzZEDBkZv5SEXMv/srbpyw5vnvIzlu8X3EmssQ5s6QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABUpTWpkpIQZNJOhxYNo4fHw1td28kruB5B+oQEEFRI2MlyWPTiSJ8bs9ECkUjg2DC1oTmdr/EIQEjnvY2+n4WQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABw0ABQLsXRUADQAJAwAAAAAAAAAAEAYABAAJDgsBARAGAAUACg4LAQEGEAIIBgQFAwcJCgALCw8MBgEWzALDkTWRkc15CQAAhAkAABAnAAAAAAYPCAIAAwcEBQkKCwsPDAYBFHC/ZasckH+7eQkAAIQJAAAAAAAABgYCAAAMBgEIrlojc7ook+I=
        """.stripTrailing());

    final var skeleton = TransactionSkeleton.deserializeSkeleton(data);
    final var instructions = skeleton.parseLegacyInstructions();
    assertEquals(7, instructions.length);

    var withdrawIx = instructions[4];
    assertEquals(metAccounts.dlmmProgram(), withdrawIx.programId().publicKey());
    var accounts = withdrawIx.accounts();
    assertEquals(16, accounts.size());

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(lbPair), accounts.get(1));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.get(2));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(3));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(4));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(6));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(7));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(8));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(9));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(10));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(11));
    assertEquals(AccountMeta.createRead(solAccounts.readMemoProgramV2().publicKey()), accounts.get(12));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(13));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.get(14));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.getLast());

    var removeLiquidityData = LbClmmProgram.RemoveLiquidityByRange2IxData.read(withdrawIx);
    assertEquals(REMOVE_LIQUIDITY_BY_RANGE2_DISCRIMINATOR, removeLiquidityData.discriminator());
    assertEquals(lowerBinId, removeLiquidityData.fromBinId());
    assertEquals(upperBinId, removeLiquidityData.toBinId());
    assertEquals(DlmmUtils.BASIS_POINT_MAX, removeLiquidityData.bpsToRemove());
    assertEquals(0, removeLiquidityData.remainingAccountsInfo().slices().length);

    var removeLiquidityByRangeIx = dlmmClient.removeLiquidityByRange(
        position,
        lbPair,
        null,
        userTokenX, userTokenY,
        reserveX, reserveY,
        xMint, yMint,
        solAccounts.tokenProgram(), solAccounts.tokenProgram(),
        removeLiquidityData.fromBinId(), removeLiquidityData.toBinId(),
        DlmmUtils.BASIS_POINT_MAX,
        null
    ).extraAccounts(binArrayAccountMetas);

    assertEquals(metAccounts.invokedDlmmProgram(), removeLiquidityByRangeIx.programId());
    accounts = removeLiquidityByRangeIx.accounts();
    assertEquals(16, accounts.size());

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(lbPair), accounts.get(1));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.get(2));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(3));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(4));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(6));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(7));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(8));
    assertEquals(AccountMeta.createReadOnlySigner(feePayer), accounts.get(9));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(10));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(11));
    assertEquals(AccountMeta.createRead(solAccounts.readMemoProgramV2().publicKey()), accounts.get(12));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(13));
    assertEquals(AccountMeta.createRead(metAccounts.dlmmProgram()), accounts.get(14));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.getLast());

    assertArrayEquals(
        Arrays.copyOfRange(withdrawIx.data(), withdrawIx.offset(), withdrawIx.offset() + withdrawIx.len()),
        removeLiquidityByRangeIx.data()
    );

    var claimFeeIx = instructions[5];
    assertEquals(metAccounts.dlmmProgram(), claimFeeIx.programId().publicKey());
    accounts = claimFeeIx.accounts();
    assertEquals(15, accounts.size());

    var claimFeeData = LbClmmProgram.ClaimFee2IxData.read(claimFeeIx);
    assertEquals(CLAIM_FEE2_DISCRIMINATOR, claimFeeData.discriminator());
    assertEquals(lowerBinId, claimFeeData.minBinId());
    assertEquals(upperBinId, claimFeeData.maxBinId());
    assertEquals(0, claimFeeData.remainingAccountsInfo().slices().length);

    assertEquals(AccountMeta.createWrite(lbPair), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(position), accounts.get(1));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(2));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(3));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(4));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(6));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(7));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(8));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(9));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(10));
    assertEquals(AccountMeta.createRead(solAccounts.readMemoProgramV2().publicKey()), accounts.get(11));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(12));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.get(13));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.getLast());

    var claimFeeIx2 = dlmmClient.claimFee(
        lbPair,
        position,
        reserveX, reserveY,
        userTokenX, userTokenY,
        xMint, yMint,
        solAccounts.tokenProgram(), solAccounts.tokenProgram(),
        removeLiquidityData.fromBinId(), removeLiquidityData.toBinId(),
        null
    ).extraAccounts(binArrayAccountMetas);

    assertEquals(metAccounts.invokedDlmmProgram(), claimFeeIx2.programId());
    accounts = claimFeeIx2.accounts();
    assertEquals(15, accounts.size());

    assertEquals(AccountMeta.createWrite(lbPair), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(position), accounts.get(1));
    assertEquals(AccountMeta.createReadOnlySigner(feePayer), accounts.get(2));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(3));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(4));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(6));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(7));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(8));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(9));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(10));
    assertEquals(AccountMeta.createRead(solAccounts.readMemoProgramV2().publicKey()), accounts.get(11));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(12));
    assertEquals(AccountMeta.createRead(metAccounts.dlmmProgram()), accounts.get(13));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.getLast());

    assertArrayEquals(
        Arrays.copyOfRange(claimFeeIx.data(), claimFeeIx.offset(), claimFeeIx.offset() + claimFeeIx.len()),
        claimFeeIx2.data()
    );

    var closePositionIx = instructions[6];
    assertEquals(metAccounts.dlmmProgram(), closePositionIx.programId().publicKey());
    accounts = closePositionIx.accounts();
    assertEquals(6, accounts.size());

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(1));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(2));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(3));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.get(4));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.getLast());

    var closePositionIx2 = dlmmClient.closePosition(position).extraAccounts(binArrayAccountMetas);

    assertEquals(metAccounts.invokedDlmmProgram(), closePositionIx2.programId());
    accounts = closePositionIx2.accounts();
    assertEquals(6, accounts.size());

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createReadOnlySigner(feePayer), accounts.get(1));
    assertEquals(AccountMeta.createWrite(feePayer), accounts.get(2));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(3));
    assertEquals(AccountMeta.createRead(metAccounts.dlmmProgram()), accounts.get(4));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.getLast());

    assertArrayEquals(
        Arrays.copyOfRange(closePositionIx.data(), closePositionIx.offset(), closePositionIx.offset() + closePositionIx.len()),
        closePositionIx2.data()
    );
  }

  void testAddLiquidityByStrategy2() {
    final byte[] data = Base64.getDecoder().decode("""
        AgbEjRDSiisDGnrKSlbmFewlI3mxknJIu7kBxl0u1zJ43xRa+ITnJ965SQoWblTdLAS/KwkO7uhNq3xPD12diAd9hErqoRbpeJXJmWJ9OzYCzzAl3JLrQ+BvxJJ5c9TCyp4fFhxVwVwap7e4nyCn6krwq9LZs2Uc3XbIih0XEroOAgAJEQz1ZengdKrWEpWIn+G9LR1uGXzK80nLXfPctzy4YLIGUKwES1Vl+1gtaL8lUk/ukzzCG37+c1V9fQa7Qe06lMgYIFZ4uR1jykznDpUeKFhL/hHz2WQgj8PB24QmYx25+E/RFjpDRb9+vPCBvy2cQZJqmIOq6HIeF+SanWa4K6XNWqA+yQzqjmLux3w7/C2B02Tqi6U0esHGgo87U30CvG+OzdiddVSoOFDTXcEcLijCzeBdioHPFW72ST8uULYUCowIeNmN9cdHY/UQGivg/qvFJW4EWi8BOpWmxdLuDUHUmS9Ey1SNJLcTRhZw86IgZHbYFd4s7otVCEPkBDi60SIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIyXJY9OJInxuz0QKRSODYMLWhOZ2v8QhASOe9jb6fhZAwZGb+UhFzL/7K26csOb57yM5bvF9xJrLEObOkAAAACycNZ/qYxRzwITBRNYliuvNXQr7VnJ2URenA0MhcfNkQTp4S+8hOgmyTLM6eJkDM4VWQwcYnOwklcIujuFILC8C2K6B09yLJ1BFPLY9woAxmACM3ub+QyHNlem0gHbTIAGm4hX/quBhPtof2NGGMA12sQ53BrrO1WYoPAAAAAAAQan1RcZLFxRIYzJTD1K8X9Y2u4Im6H9ROPb2YoAAAAABt324ddloZPZy+FGzut5rBy0he1fWzeROoz1hX7/AKkMq5vJoCWD+PYS1pW9N0Wt6okiRnFPuHkDzGJz8tGejwkKAAUChj0DAAwIAAECAAgPCwwQ28DqR76/ZlB5CQAADAAAAAkGAAYADQgQAQEJBgAHAA4IEAEBCAIABwwCAAAA+rAIGQAAAAAQAQcBEQwPAQIMBgcEBQ0OABAQCwwDcQPdldpvjXbVAAAAAAAAAAD6sAgZAAAAAJsJAAAKAAAAeQkAAIQJAAAIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIAAAAAAAEAEAMHAAABCQoACQPtLAAAAAAAAA==
        """.stripTrailing());

    var skeleton = TransactionSkeleton.deserializeSkeleton(data);
    var instructions = skeleton.parseLegacyInstructions();
    assertEquals(9, instructions.length);

    final var addLiquidityInstruction = instructions[6];

    final var ixData = LbClmmProgram.AddLiquidityByStrategy2IxData.read(addLiquidityInstruction);
    System.out.println(ixData);
  }
}
