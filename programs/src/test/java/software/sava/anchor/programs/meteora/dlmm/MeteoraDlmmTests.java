package software.sava.anchor.programs.meteora.dlmm;

import org.junit.jupiter.api.Test;
import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.TransactionSkeleton;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram.REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

final class MeteoraDlmmTests {

  @Test
  public void testWithdrawAndClosePosition() {
    final var solAccounts = SolanaAccounts.MAIN_NET;
    final var metAccounts = MeteoraAccounts.MAIN_NET;

    final var feePayer = PublicKey.fromBase58Encoded("savaKKJmmwDsHHhxV6G293hrRM4f1p6jv6qUF441QD3");
    final var nativeAccountClient = NativeProgramAccountClient.createClient(feePayer);
    final var dlmmClient = MeteoraDlmmClient.createClient(nativeAccountClient);

    var position = fromBase58Encoded("3kiuXd5MYdZHYBi7M4JN1gReHKbYAawNKp5Qu6NCq81s");
    var lbPair = fromBase58Encoded("7ubS3GccjhQY99AYNKXjNJqnXjaokEdfdV915xnCb96r");
    var userTokenX = fromBase58Encoded("APXcu3pekbBg9vQCiXSRFrfi5So1WMWwWFPfFWWrEtRd");
    var userTokenY = fromBase58Encoded("6mMCJrZj65Y6YYvxkN3SMG6Rp8wbCFoYNikogAVbWxTP");
    var reserveX = fromBase58Encoded("81GLeor2tJ9dut4YUE7BzGE4WfTtD4LbvMwLsrmw7wTg");
    var reserveY = fromBase58Encoded("9mBxhiBNki6FjcmXeFGfKDf6o4fGXZ8JtG4jxNDeiv6u");
    var xMint = fromBase58Encoded("cbbtcf3aa214zXHbiAZQwf4122FBYbraNdFqgw4iMij");
    var yMint = fromBase58Encoded("EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v");
    var binArrayLower = fromBase58Encoded("Dvc26RoDiWjTNEYgjXeJcQzvSUDBLmBsfE3qdFNz87ho");
    var binArrayUpper = fromBase58Encoded("C1hytWutcuBiVfzZ1aWsQFaiPmQ2ZuubjGmXQ6i296Bj");

    final byte[] data = Base64.getDecoder().decode("""
        AQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAUPDPVl6eB0qtYSlYif4b0tHW4ZfMrzSctd89y3PLhgsgZVptMisqbLPyOg7fZs2CQOE2DMuXlL92ZesAuwoosCnoIvcaabwzb5Xwz716AtZNyfeliY7G4hkFwJfcvp+vHCZp9BdL0pvK5f+U4bKek1/C1GEGxxLi5lwN5oXbBFYlOjnuSzdPAQON1JegxUBEfLiPuCv00dTpMU1d6utSIL0mgTJvNB6AFBBQo0CY/JUlbLNfPkiYoX7c/7efde54ezBOnhL7yE6CbJMszp4mQMzhVZDBxic7CSVwi6O4UgsLwo6rtEvQ8ELxTzGhysMZHRjuE4vB+KkS616/w2rAGVvsAHnzegnnEt4Y0glkzQZYqYmipUStCh6Ob1If3x7Xdei36UwGPC91tyb3e4r0kn2Lc2xUUQEIXqcI6TpfK2FTjG+nrzvtutOj1l82qryXQxsbvkwtL24OR8pgIDRS9dYQbd9uHXZaGT2cvhRs7reawctIXtX1s3kTqM9YV+/wCpsnDWf6mMUc8CEwUTWJYrrzV0K+1ZydlEXpwNDIXHzZEDBkZv5SEXMv/srbpyw5vnvIzlu8X3EmssQ5s6QAAAAAkec9F6VSbUSOWJrqWv58Is1hxbZqhqQnqyYjCVFOVcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFDQAFAsBcFQANAAkDAAAAAAAAAAAGEAcDBgkBBQIOCggEAAsLDAYSGlJmmPBKaRpFQwAAhUMAABAnBg4DBwgEAAUCCQEOCgsMBgipIE+JiOhGiQYIBwMIBAAADAYIe4ZRADFEYmI=
        """.stripTrailing());

    final var skeleton = TransactionSkeleton.deserializeSkeleton(data);
    final var instructions = skeleton.parseLegacyInstructions();
    assertEquals(5, instructions.length);

    var withdrawIx = instructions[2];
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
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.get(9));
    assertEquals(AccountMeta.createWrite(binArrayUpper), accounts.get(10));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(11));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(12));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(13));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(14));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.getLast());

    var removeLiquidityData = LbClmmProgram.RemoveLiquidityByRangeIxData.read(withdrawIx);
    assertEquals(REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR, removeLiquidityData.discriminator());
    assertEquals(17221, removeLiquidityData.fromBinId());
    assertEquals(17285, removeLiquidityData.toBinId());
    assertEquals(DlmmUtils.BASIS_POINT_MAX, removeLiquidityData.bpsToRemove());

    var removeLiquidityByRangeIx = dlmmClient.removeLiquidityByRange(
        position,
        lbPair,
        null,
        userTokenX, userTokenY,
        reserveX, reserveY,
        xMint, yMint,
        solAccounts.tokenProgram(), solAccounts.tokenProgram(),
        removeLiquidityData.fromBinId(), removeLiquidityData.toBinId(),
        DlmmUtils.BASIS_POINT_MAX
    );

    assertEquals(metAccounts.invokedDlmmProgram(), removeLiquidityByRangeIx.programId());
    accounts = removeLiquidityByRangeIx.accounts();

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(lbPair), accounts.get(1));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.get(2));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(3));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(4));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(6));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(7));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(8));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.get(9));
    assertEquals(AccountMeta.createWrite(binArrayUpper), accounts.get(10));
    assertEquals(AccountMeta.createReadOnlySigner(feePayer), accounts.get(11));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(12));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(13));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(14));
    assertEquals(AccountMeta.createRead(metAccounts.dlmmProgram()), accounts.getLast());

    assertArrayEquals(
        Arrays.copyOfRange(withdrawIx.data(), withdrawIx.offset(), withdrawIx.offset() + withdrawIx.len()),
        removeLiquidityByRangeIx.data()
    );


    var claimFeeIx = instructions[3];
    assertEquals(metAccounts.dlmmProgram(), claimFeeIx.programId().publicKey());
    accounts = claimFeeIx.accounts();
    assertEquals(14, accounts.size());

    assertEquals(AccountMeta.createWrite(lbPair), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(position), accounts.get(1));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.get(2));
    assertEquals(AccountMeta.createWrite(binArrayUpper), accounts.get(3));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(4));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(6));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(7));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(8));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(9));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(10));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(11));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(12));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.getLast());

    var claimFeeIx2 = dlmmClient.claimFee(
        lbPair,
        position,
        removeLiquidityData.fromBinId(), removeLiquidityData.toBinId(),
        reserveX, reserveY,
        userTokenX, userTokenY,
        xMint, yMint,
        solAccounts.tokenProgram()
    );

    assertEquals(metAccounts.invokedDlmmProgram(), claimFeeIx2.programId());
    accounts = claimFeeIx2.accounts();

    assertEquals(AccountMeta.createWrite(lbPair), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(position), accounts.get(1));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.get(2));
    assertEquals(AccountMeta.createWrite(binArrayUpper), accounts.get(3));
    assertEquals(AccountMeta.createReadOnlySigner(feePayer), accounts.get(4));
    assertEquals(AccountMeta.createWrite(reserveX), accounts.get(5));
    assertEquals(AccountMeta.createWrite(reserveY), accounts.get(6));
    assertEquals(AccountMeta.createWrite(userTokenX), accounts.get(7));
    assertEquals(AccountMeta.createWrite(userTokenY), accounts.get(8));
    assertEquals(AccountMeta.createRead(xMint), accounts.get(9));
    assertEquals(AccountMeta.createRead(yMint), accounts.get(10));
    assertEquals(AccountMeta.createRead(solAccounts.tokenProgram()), accounts.get(11));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(12));
    assertEquals(AccountMeta.createRead(metAccounts.dlmmProgram()), accounts.getLast());

    assertArrayEquals(
        Arrays.copyOfRange(claimFeeIx.data(), claimFeeIx.offset(), claimFeeIx.offset() + claimFeeIx.len()),
        claimFeeIx2.data()
    );

    var closePositionIx = instructions[4];
    assertEquals(metAccounts.dlmmProgram(), closePositionIx.programId().publicKey());
    accounts = closePositionIx.accounts();
    assertEquals(8, accounts.size());

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(lbPair), accounts.get(1));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.get(2));
    assertEquals(AccountMeta.createWrite(binArrayUpper), accounts.get(3));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(4));
    assertEquals(AccountMeta.createFeePayer(feePayer), accounts.get(5));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(6));
    assertEquals(AccountMeta.createWrite(metAccounts.dlmmProgram()), accounts.getLast());

    var closePositionIx2 = dlmmClient.closePosition(
        position,
        lbPair,
        removeLiquidityData.fromBinId(), removeLiquidityData.toBinId()
    );

    assertEquals(metAccounts.invokedDlmmProgram(), closePositionIx2.programId());
    accounts = closePositionIx2.accounts();

    assertEquals(AccountMeta.createWrite(position), accounts.getFirst());
    assertEquals(AccountMeta.createWrite(lbPair), accounts.get(1));
    assertEquals(AccountMeta.createWrite(binArrayLower), accounts.get(2));
    assertEquals(AccountMeta.createWrite(binArrayUpper), accounts.get(3));
    assertEquals(AccountMeta.createReadOnlySigner(feePayer), accounts.get(4));
    assertEquals(AccountMeta.createWrite(feePayer), accounts.get(5));
    assertEquals(AccountMeta.createRead(metAccounts.eventAuthority().publicKey()), accounts.get(6));
    assertEquals(AccountMeta.createRead(metAccounts.dlmmProgram()), accounts.getLast());

    assertArrayEquals(
        Arrays.copyOfRange(closePositionIx.data(), closePositionIx.offset(), closePositionIx.offset() + closePositionIx.len()),
        closePositionIx2.data()
    );
  }
}
